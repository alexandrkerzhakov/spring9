package ru.gb.virusTotal.controller;

import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.virusTotal.model.*;
import ru.gb.virusTotal.properties.VirusTotalProperties;
import ru.gb.virusTotal.service.VirusTotalService;

import java.io.IOException;

@Controller
@RequestMapping("/virusTotal")
public class VirusTotalController {

    @Autowired
    private VirusTotalProperties virusTotalProperties;
    @Autowired
    private VirusTotalService virusTotalService;

    @GetMapping("/ip/{ip}")
    public String getIpInfo(@PathVariable("ip") String ip, Model model) {

        Request request = virusTotalService.createGetRequest(virusTotalProperties.getBASE_URL(), "ip_addresses/", ip, "");
        String json = virusTotalService.getResponseString(request).orElseThrow();
        IpInfo ipInfoInstance = virusTotalService.getInstance(json, IpInfo.class);
        model.addAttribute("ipInfoInstance", ipInfoInstance);

        return "virus_total/ip_info";

    }

    @GetMapping("/ip/{ip}/comments")
    public String getCommentsOnIp(@PathVariable("ip") String ip, Model model) {

        Request request = virusTotalService.createGetRequest(virusTotalProperties.getBASE_URL(), "ip_addresses/", ip, "/comments");
        String json = virusTotalService.getResponseString(request).orElseThrow();
        IpComments ipCommentsInstance = virusTotalService.getInstance(json, IpComments.class);
        model.addAttribute("ipCommentsInstance", ipCommentsInstance);

        return "virus_total/ip_comments";
    }

    @GetMapping("/ip/{ip}/votes")
    public String getVotesOnIp(@PathVariable("ip") String ip, Model model) {

        Request request = virusTotalService.createGetRequest(virusTotalProperties.getBASE_URL(), "ip_addresses/", ip, "/votes");
        String json = virusTotalService.getResponseString(request).orElseThrow();
        IpVotes ipVotesInstance = virusTotalService.getInstance(json, IpVotes.class);
        model.addAttribute("ipVotesInstance", ipVotesInstance);

        return "virus_total/ip_votes";
    }

    @GetMapping("/domains/{domain}")
    public String getDomainInfo(@PathVariable("domain") String domain, Model model) {

        Request request = virusTotalService.createGetRequest(virusTotalProperties.getBASE_URL(), "domains/", domain, "");
        String json = virusTotalService.getResponseString(request).orElseThrow();
        DomainInfo domainInfoInstance = virusTotalService.getInstance(json, DomainInfo.class);

        model.addAttribute("domainInfoInstance", domainInfoInstance);
        return "virus_total/domain_info";

    }

    @GetMapping("/domains/{domain}/comments")
    public String getCommentsOnDomain(@PathVariable("domain") String domain, Model model) {

        Request request = virusTotalService.createGetRequest(virusTotalProperties.getBASE_URL(), "domains/", domain, "/comments");
        String json = virusTotalService.getResponseString(request).orElseThrow();
        DomainComments domainCommentsInstance = virusTotalService.getInstance(json, DomainComments.class);

        model.addAttribute("domainCommentsInstance", domainCommentsInstance);
        return "virus_total/domain_comments";
    }

    @GetMapping("/domains/{domain}/votes")
    public String getVotesOnDomain(@PathVariable("domain") String domain, Model model) {

        Request request = virusTotalService.createGetRequest(virusTotalProperties.getBASE_URL(), "domains/", domain, "/votes");
        String json = virusTotalService.getResponseString(request).orElseThrow();
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
            postRequest = virusTotalService.createPostRequest(virusTotalProperties.getBASE_URL(), "files", file);
        } else {
            Request getRequestForUploadUrl = virusTotalService.createGetRequest(virusTotalProperties.getBASE_URL(), "files", "", "/upload_url");
            String getJsonForUploadUrl = virusTotalService.getResponseString(getRequestForUploadUrl).orElseThrow();
            DataUpload dataUploadInstance = virusTotalService.getInstance(getJsonForUploadUrl, DataUpload.class);
            postRequest = virusTotalService.createPostRequest("", dataUploadInstance.getData(), file);
        }

        postJson = virusTotalService.getResponseString(postRequest).orElseThrow();
        uploadFileInstance = virusTotalService.getInstance(postJson, UploadFile.class);

        Request getRequest = virusTotalService.createGetRequest(virusTotalProperties.getBASE_URL(), "analyses/", uploadFileInstance.getData().getId(), "");
        String getJson = virusTotalService.getResponseString(getRequest).orElseThrow();
        analysesFileInstance = virusTotalService.getInstance(getJson, AnalysesFile.class);

        model.addAttribute("analysesFileInstance", analysesFileInstance);
        return "virus_total/analyses_file";
    }

    @GetMapping("/analyses/{id}")
    public String getResponse(@PathVariable("id") String id, Model model) {

        Request request = virusTotalService.createGetRequest(virusTotalProperties.getBASE_URL(), "analyses/", id, "");
        String json = virusTotalService.getResponseString(request).orElseThrow();
        AnalysesFile analysesFileInstance = virusTotalService.getInstance(json, AnalysesFile.class);
        model.addAttribute("analysesFileInstance", analysesFileInstance);

        return "virus_total/analyses_file";
    }

//    @PostMapping("/urlDownload")
//    public String getUrlForDownload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getBytes().length);
//
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("file", file.getOriginalFilename(),
//                        okhttp3.RequestBody.create(file.getBytes(), MediaType.parse("application/octet-stream")));
//
//        Request request = new Request.Builder()
//                .url("https://www.virustotal.com/api/v3/files/")
//                .post(builder.build())
//                .header("x-apikey", "05c9a3dde9d7257a4968674470599f6639f35dfb14477c924da548ea4f85e2a9")
//                .addHeader("content-type", "multipart/form-data")
//                .addHeader("accept", "application/json")
//                .build();
//
//        String json = "";
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println("response" + response);
//            assert response.body() != null;
//            json = response.body().string();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        System.out.println(json);
//
//
//        model.addAttribute("json", json);
//
//        return "virus_total/response3";
//    }
}