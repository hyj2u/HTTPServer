package server2.handler;

import server2.request.Request;
import server2.response.Response;
import server2.response.ResponseStatus;
import server2.util.FileContentConverter;
import server2.util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RangeResponder {
    private String rootPath;
    private FileContentConverter fileContentConverter;
    private ResourceTypeIdentifier resourceTypeIdentifier;

    public RangeResponder(String rootPath, FileContentConverter fileContentConverter, ResourceTypeIdentifier resourceTypeIdentifier) {
        this.rootPath = rootPath;
        this.fileContentConverter = fileContentConverter;
        this.resourceTypeIdentifier = resourceTypeIdentifier;
    }
    private String getRangePart(Request request){
        String rangeSpec = request.getHeaders().get("Range");
        return rangeSpec.replaceAll("[^0-9-0-9]+", "").trim();
    }
    private boolean bothLimitsExists(String rangeSlice){
        return rangeSlice.matches("[0-9]+-[0-9]+");
    }
    private boolean firstLimitonly(String rangeSlice){
        return rangeSlice.matches("[0-9]+-");
    }
    private ArrayList<Integer> getRangeLimits(byte[] fullContents, String rangeSlice){
        int first;
        int last;
        String[] rangeNumbers = rangeSlice.split("-");
        if(bothLimitsExists(rangeSlice)){
            first = Integer.parseInt(rangeNumbers[0]);
            last =Integer.parseInt(rangeNumbers[1]);
        }else if(firstLimitonly(rangeSlice)){
            first =Integer.parseInt(rangeNumbers[0]);
            last =fullContents.length-1;
        }else{
            int number = Integer.parseInt(rangeNumbers[1]);
            first = fullContents.length - number;
            last = fullContents.length-1;
        }
        return new ArrayList<>(Arrays.asList(first,last));
    }
    private boolean outOfBounds(byte[] contents, int number){
        return ((number >contents.length )|| (number<0));
    }
    private boolean outOfRange(byte[] fullContents, int first, int last){
        return outOfBounds(fullContents, first) || outOfBounds(fullContents, last);
    }
    private Response setRangeNotSatisfiableResponse(byte[] fullContents){
        Response response = new Response();
        response.setContentRangeHeader("bytes "+ "*/"+Integer.toString(fullContents.length));
        response.setBodyContent(ResponseStatus.RANGENOTSATISFIABLE.getStatusBodyAsByte());
        response.setResponseStatus(ResponseStatus.RANGENOTSATISFIABLE);
        return response;
    }
    private Response setRangePartialContentResponse(byte[] fullContents, int first, int last) {
        Response response = new Response();
        byte[] specifiedContents = fileContentConverter.getPartialContent(fullContents, first, last);
        response.setBodyContent(specifiedContents);
        response.setContentRangeHeader("bytes " + Integer.toString(first) + "-" + Integer.toString(last) + "/" + Integer.toString(fullContents.length));
        response.setResponseStatus(ResponseStatus.PARTIALCONTENT);
        return response;
    }
    private Response getResponse(byte[] fullContents, int first, int last){
        if(outOfRange(fullContents, first, last)){
            return setRangeNotSatisfiableResponse(fullContents);
        }else{
            return setRangePartialContentResponse(fullContents, first, last);
        }

    }
    public Response doRange(Request request) throws IOException {
        File resource = new File((rootPath+request.getResourcePath()));
        byte[] fullContetns = fileContentConverter.getFullContents(resource);
        String rangeSlice = getRangePart(request);
        ArrayList<Integer> rangeLimits = getRangeLimits(fullContetns, rangeSlice);
        Response response = getResponse(fullContetns, rangeLimits.get(0), rangeLimits.get(1));
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        return response;
    }


}
