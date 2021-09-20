package com.example.webmvc.ipStuff;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class IPAddressServiceImpl implements IPAddressService {

    @Override
    public String getRemoteIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    @Override
    public String getIPVersion(HttpServletRequest request) {
        if (getRemoteIP(request).contains(":")) {
            return "IPv6";
        }
        return "IPv4";
    }
}
