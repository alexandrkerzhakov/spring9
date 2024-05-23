package ru.gb.virusTotal.controller;

import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.virusTotal.config.FileWriteGateWay;
import ru.gb.virusTotal.model.*;
import ru.gb.virusTotal.properties.VirusTotalProperties;
import ru.gb.virusTotal.service.VirusTotalService;

import java.io.IOException;

@Controller
@RequestMapping("/virusTotal")
public class VirusTotalController {

    @Autowired
    private VirusTotalService virusTotalService;

    @Autowired
    private FileWriteGateWay fileWriteGateWay;

    @GetMapping("/ip/{ip}")
    public String getIpInfo(@PathVariable("ip") String ip, Model model) {

        Request request = virusTotalService.createGetRequest("ip_addresses/", ip, "");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        fileWriteGateWay.writeToFile("ipInfo.txt", json);
        IpInfo ipInfoInstance = virusTotalService.getInstance(json, IpInfo.class);
        model.addAttribute("ipInfoInstance", ipInfoInstance);

        return "virus_total/ip_info";

    }

    @GetMapping("/ip/{ip}/comments")
    public String getCommentsOnIp(@PathVariable("ip") String ip, Model model) {

        Request request = virusTotalService.createGetRequest("ip_addresses/", ip, "/comments");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        IpComments ipCommentsInstance = virusTotalService.getInstance(json, IpComments.class);
        model.addAttribute("ipCommentsInstance", ipCommentsInstance);

        return "virus_total/ip_comments";
    }

    @GetMapping("/ip/{ip}/votes")
    public String getVotesOnIp(@PathVariable("ip") String ip, Model model) {

        Request request = virusTotalService.createGetRequest("ip_addresses/", ip, "/votes");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        IpVotes ipVotesInstance = virusTotalService.getInstance(json, IpVotes.class);
        model.addAttribute("ipVotesInstance", ipVotesInstance);

        return "virus_total/ip_votes";
    }

    @GetMapping("/domains/{domain}")
    public String getDomainInfo(@PathVariable("domain") String domain, Model model) {

        Request request = virusTotalService.createGetRequest("domains/", domain, "");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        DomainInfo domainInfoInstance = virusTotalService.getInstance(json, DomainInfo.class);

        model.addAttribute("domainInfoInstance", domainInfoInstance);
        return "virus_total/domain_info";

    }

    @GetMapping("/domains/{domain}/comments")
    public String getCommentsOnDomain(@PathVariable("domain") String domain, Model model) {

        Request request = virusTotalService.createGetRequest("domains/", domain, "/comments");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        DomainComments domainCommentsInstance = virusTotalService.getInstance(json, DomainComments.class);

        model.addAttribute("domainCommentsInstance", domainCommentsInstance);
        return "virus_total/domain_comments";
    }

    @GetMapping("/domains/{domain}/votes")
    public String getVotesOnDomain(@PathVariable("domain") String domain, Model model) {

        Request request = virusTotalService.createGetRequest("domains/", domain, "/votes");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        DomainVotes domainVotesInstance = virusTotalService.getInstance(json, DomainVotes.class);

        model.addAttribute("domainVotesInstance", domainVotesInstance);
        return "virus_total/domain_votes";
    }

    @GetMapping("/getupload")
    public String fileUpload() {
        return "virus_total/upload_file";
    }

    @PostMapping("/upload")
    public String autoFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {

        UploadFile uploadFileInstance;
        AnalysesFile analysesFileInstance;
        Request postRequest;
        String postJson;

        if (file.getSize() < 32768) {
            postRequest = virusTotalService.createPostRequest("files", file);
        } else {
            Request getRequestForUploadUrl = virusTotalService.createGetRequest( "files", "", "/upload_url");
            String getJsonForUploadUrl = virusTotalService.createResponseString(getRequestForUploadUrl).orElseThrow();
            DataUpload dataUploadInstance = virusTotalService.getInstance(getJsonForUploadUrl, DataUpload.class);
            postRequest = virusTotalService.createDopPostRequest(dataUploadInstance.getData(), file);
        }

        postJson = virusTotalService.createResponseString(postRequest).orElseThrow();
        uploadFileInstance = virusTotalService.getInstance(postJson, UploadFile.class);

        Request getRequest = virusTotalService.createGetRequest( "analyses/", uploadFileInstance.getData().getId(), "");
        String getJson = virusTotalService.createResponseString(getRequest).orElseThrow();
        analysesFileInstance = virusTotalService.getInstance(getJson, AnalysesFile.class);

        model.addAttribute("analysesFileInstance", analysesFileInstance);
        return "virus_total/analyses_file";
    }

    @GetMapping("/analyses/{id}")
    public String getResponse(@PathVariable("id") String id, Model model) {

        Request request = virusTotalService.createGetRequest("analyses/", id, "");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        AnalysesFile analysesFileInstance = virusTotalService.getInstance(json, AnalysesFile.class);
        model.addAttribute("analysesFileInstance", analysesFileInstance);

        return "virus_total/analyses_file";
    }
}