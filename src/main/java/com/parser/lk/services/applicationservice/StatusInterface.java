package com.parser.lk.services.applicationservice;

public interface StatusInterface {

    public boolean doProcess(Long orderId);

    public String getStatusName();

    public String getNextStatusName();

}
