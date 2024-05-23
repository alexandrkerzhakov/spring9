package ru.gb.kaspersky.controller;

import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.kaspersky.config.FileWriteGateWay;
import ru.gb.kaspersky.model.AnalysesFile;
import ru.gb.kaspersky.model.DomainInfo;
import ru.gb.kaspersky.model.IpInfo;
import ru.gb.kaspersky.model.UrlInfo;
import ru.gb.kaspersky.service.KasperskyService;

import java.io.IOException;

@Controller
@RequestMapping("/kaspersky")
public class KasperskyController {

    @Autowired
    private KasperskyService kasperskyService;

    @Autowired
    private FileWriteGateWay fileWriteGateWay;

    @GetMapping("/ip")
    public String getIpInfo(@RequestParam("ip") String ip, Model model) {

        Request request = kasperskyService.createGetRequest("search/", "ip?request=", ip);
        String json = kasperskyService.getResponseString(request).orElseThrow();
        fileWriteGateWay.writeToFile("ipInfo.txt", json);
        IpInfo ipInfoInstance = kasperskyService.getInstance(json, IpInfo.class);
        model.addAttribute("ipInfoInstance", ipInfoInstance);

        return "kaspersky/ip_info";

    }

    @GetMapping("/domain")
    public String getDomainInfo(@RequestParam("domain") String domain, Model model) {

        Request request = kasperskyService.createGetRequest("search/", "domain?request=", domain);
        String json = kasperskyService.getResponseString(request).orElseThrow();
        DomainInfo domainInfoInstance = kasperskyService.getInstance(json, DomainInfo.class);
        model.addAttribute("domainInfoInstance", domainInfoInstance);

        return "kaspersky/domain_info";
    }

    @GetMapping("/url")
    public String getUrlInfo(@RequestParam("url") String url, Model model) {

        Request request = kasperskyService.createGetRequest("search/", "url?request=", url);
        String json = kasperskyService.getResponseString(request).orElseThrow();
        UrlInfo urlInfoInstance = kasperskyService.getInstance(json, UrlInfo.class);
        model.addAttribute("urlInfoInstance", urlInfoInstance);

        return "kaspersky/url_info";
    }

    @GetMapping("/getupload")
    public String fileUpload() {
        return "kaspersky/upload_file";
    }


    @PostMapping("/upload")
    public String autoFileUpload(@RequestBody MultipartFile file, Model model) throws IOException {

        Request request = kasperskyService.createPostRequest("scan/", "file?filename=", file);
        String json = kasperskyService.getResponseString(request).orElseThrow();
        AnalysesFile analysesFileInstance = kasperskyService.getInstance(json, AnalysesFile.class);
        model.addAttribute("analysesFileInstance", analysesFileInstance);

        return "kaspersky/analyses_file";
    }
}


