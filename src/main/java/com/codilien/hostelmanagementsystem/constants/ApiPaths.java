package com.codilien.hostelmanagementsystem.constants;

public class ApiPaths {
    /**
     * EndPoints related to Complaint
     */
    public static final String BASE_COMPLAINT_API = "/api/complaints";

    public static final String GET_ALL_COMPLAINTS = "";
    public static final String GET_COMPLAINT_BY_ID = "/{id}";
    public static final String CREATE_COMPLAINT = "/create";
    public static final String UPDATE_COMPLAINT = "/{id}/update";
    public static final String DELETE_COMPLAINT = "/{id}";


    /**
     * EndPoints related to Visitors
     */
    public static final String BASE_VISITOR_API = "/api/visitors";

    public static final String GET_ALL_VISITORS = "";
    public static final String GET_VISITOR_BY_ID = "/{id}";
    public static final String CREATE_VISITOR = "/create";
    public static final String UPDATE_VISITOR = "/{id}/update";
    public static final String DELETE_VISITOR = "/{id}";
}
