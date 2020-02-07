package handler;

import request.HttpVerb;
import request.Request;
import response.Response;
import response.ResponseStatus;
import util.ContentType;
import util.FileContentConverter;
import util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class check range request header and check resource which is fit for range.
 * After that, send proper response.
 *
 * @author Younjin Hwang
 * @version 1.0
 * @since 1.0
 */
public class RangeResponder {
    /**
     * Resource path that client requests.
     */
    private String rootPath;
    /**
     * Class that reads resource file.
     */
    private FileContentConverter fileContentConverter;
    /**
     * Class that defines the request's content-type.
     */
    private ResourceTypeIdentifier resourceTypeIdentifier;

    /**
     * This Constructor sets resource path and initializes resourceTypeIdentifier and resourceTypeIdentifier.
     *
     * @param rootPath               resource path that client requests
     * @param fileContentConverter   reads resource file
     * @param resourceTypeIdentifier defines the request's content-type
     * @see ResourceTypeIdentifier
     * @see FileContentConverter
     * @since 1.0
     */
    public RangeResponder(String rootPath, FileContentConverter fileContentConverter, ResourceTypeIdentifier resourceTypeIdentifier) {
        this.rootPath = rootPath;
        this.fileContentConverter = fileContentConverter;
        this.resourceTypeIdentifier = resourceTypeIdentifier;
    }

    /**
     * Gets request's contents of range header and parse to String.
     *
     * @param request the object that made with client input at socket
     * @return range content
     * @see Request
     * @see Request#getHeaders()
     * @see String#replaceAll(String, String)
     * @see String#trim()
     * @since 1.0
     */
    private String getRangePart(Request request) {
        String rangeSpec = request.getHeaders().get("Range");
        return rangeSpec.replaceAll("[^0-9-0-9]+", " ").trim();
    }

    /**
     * Returns if range have both limits.(eg.number - number).
     *
     * @param rangeSlice content of range header
     * @return <code>true</code> if it have both limits
     * <code>false</code> otherwise;
     * @see String#matches(String)
     * @since 1.0
     */
    private boolean bothLimitsExists(String rangeSlice) {
        return rangeSlice.matches("[0-9]+-[0-9]+");
    }

    /**
     * Returns if range has only first limit (eg.number -).
     *
     * @param rangeSlice content of range header
     * @return <code>true</code> if it has only first limit
     * <code>false</code> otherwise;
     * @see String#matches(String)
     * @since 1.0
     */
    private boolean firstLimitonly(String rangeSlice) {
        return rangeSlice.matches("[0-9]+-");
    }

    /**
     * Returns range's start number and end number as ArrayList.
     *
     * @param rangeSlice   content of range header
     * @param fullContents resource contents
     * @return ArrayList it contains limit of start and end number
     * @see String#split(String)
     * @see #bothLimitsExists(String)
     * @see Integer#parseInt(String)
     * @see #firstLimitonly(String)
     * @since 1.0
     */
    private ArrayList<Integer> getRangeLimits(byte[] fullContents, String rangeSlice) {
        int first;
        int last;

        String[] rangeNumbers = rangeSlice.split("-");

        if (bothLimitsExists(rangeSlice)) {
            first = Integer.parseInt(rangeNumbers[0]);
            last = Integer.parseInt(rangeNumbers[1]);
        } else if (firstLimitonly(rangeSlice)) {
            first = Integer.parseInt(rangeNumbers[0]);
            last = fullContents.length - 1;
        } else {
            int number = Integer.parseInt(rangeNumbers[1]);
            first = fullContents.length - number;
            last = fullContents.length - 1;
        }
        return new ArrayList<>(Arrays.asList(first, last));
    }

    /**
     * Returns if resource's contents are over than fist or end limit of range.
     *
     * @param contents resource contents
     * @param number   one of limit in range
     * @return <code>true</code> if contents' length are bigger that limit number;
     * <code>false</code> otherwise
     * @since 1.0
     */
    private boolean outOfBounds(byte[] contents, int number) {

        return ((number > contents.length) || (number < 0));
    }

    /**
     * Returns if resource's contents are over than range.
     *
     * @param fullContents resource contents
     * @param first        first limit
     * @param last         last limit
     * @return <code>true</code> contents are out of bounds of first or last number;
     * <code>false</code> otherwise;
     * @see #outOfBounds(byte[], int)
     * @since 1.0
     */
    private boolean outOfRange(byte[] fullContents, int first, int last) {
        return outOfBounds(fullContents, first) || outOfBounds(fullContents, last);
    }

    /**
     * Makes response which is Range Not Satisfiable.
     *
     * @param fullContents resource contents
     * @return response
     * @see Response
     * @see Response#setContentRangeHeader(String)
     * @see Response#setBodyContent(byte[])
     * @see ResponseStatus#getStatusBodyAsByte()
     * @see Response#setResponseStatus(ResponseStatus)
     * @see ResponseStatus
     * @since 1.0
     */
    private Response setRangeNotSatisfiableResponse(byte[] fullContents) {
        Response response = new Response();
        response.setContentRangeHeader("bytes " + "*/" + fullContents.length);
        response.setBodyContent(ResponseStatus.RANGENOTSATISFIABLE.getStatusBodyAsByte());
        response.setResponseStatus(ResponseStatus.RANGENOTSATISFIABLE);
        return response;
    }

    /**
     * Makes response which is Partial Content.
     *
     * @param fullContents resource contents
     * @param first        first limit
     * @param last         last limit
     * @return response
     * @see Response
     * @see FileContentConverter#getPartialContent(byte[], int, int)
     * @see Response#setContentRangeHeader(String)
     * @see Response#setBodyContent(byte[])
     * @see Response#setResponseStatus(ResponseStatus)
     * @see ResponseStatus
     * @since 1.0
     */
    private Response setRangePartialContentResponse(byte[] fullContents, int first, int last) {
        Response response = new Response();

        byte[] specifiedContents = fileContentConverter.getPartialContent(fullContents, first, last);
        response.setBodyContent(specifiedContents);
        response.setContentRangeHeader("bytes " + first + "-" + last + "/" + fullContents.length);
        response.setResponseStatus(ResponseStatus.PARTIALCONTENT);
        return response;
    }

    /**
     * If request's range is bigger than contents, set range not satisfiable response.
     * Otherwise, set partial content range.
     *
     * @param fullContents resource contents
     * @param first        first limit
     * @param last         last limit
     * @return response
     * @see #outOfRange(byte[], int, int)
     * @see #setRangeNotSatisfiableResponse(byte[])
     * @see #setRangePartialContentResponse(byte[], int, int)
     * @since 1.0
     */
    private Response getResponse(byte[] fullContents, int first, int last) {
        if (outOfRange(fullContents, first, last)) {
            return setRangeNotSatisfiableResponse(fullContents);
        } else {
            return setRangePartialContentResponse(fullContents, first, last);
        }
    }

    /**
     * This method gets full contents of resource and check with range header.
     * Then, send right response.
     * @param request the object that made with client input at socket
     * @return response object for client output
     * @throws IOException when request is null
     * @see File
     * @see Request#getResourcePath()
     * @see FileContentConverter#getFullContents(File)
     * @see #getRangePart(Request)
     * @see #getRangeLimits(byte[], String)
     * @see Response#setContentTypeHeader(ContentType)
     * @see ResourceTypeIdentifier#getType(File)
     * @since 1.0
     */
    public Response doRange(Request request) throws IOException {
        File resource = new File((rootPath + request.getResourcePath()));
        byte[] fullContents = fileContentConverter.getFullContents(resource);
        String rangeSlice = getRangePart(request);

        ArrayList<Integer> rangeLimits = getRangeLimits(fullContents, rangeSlice);
        Response response = getResponse(fullContents, rangeLimits.get(0), rangeLimits.get(1));
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        return response;
    }


}
