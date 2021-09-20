package com.example.webmvc.ipStuff;

import javax.servlet.http.HttpServletRequest;

public interface IPAddressService {
    String getRemoteIP(HttpServletRequest request);

    String getIPVersion(HttpServletRequest request);
}
