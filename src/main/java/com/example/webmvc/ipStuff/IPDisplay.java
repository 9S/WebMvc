package com.example.webmvc.ipStuff;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IPDisplay {
    private final IPAddressService ipAddressService;

    public IPDisplay(IPAddressService ipAddressService) {
        this.ipAddressService = ipAddressService;
    }

    @GetMapping("/ip")
    public String getIP(HttpServletRequest request, Model model) {
        model.addAttribute("ipAddress", ipAddressService.getRemoteIP(request));
        model.addAttribute("ipVersion", ipAddressService.getIPVersion(request));
        return "ip";
    }
}
