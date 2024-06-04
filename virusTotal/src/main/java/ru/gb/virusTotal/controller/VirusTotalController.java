package ru.gb.virusTotal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.virusTotal.config.VirusTotalFileWriteGateWay;
import ru.gb.virusTotal.model.*;
import ru.gb.virusTotal.service.VirusTotalService;

import java.io.IOException;

@Controller
@RequestMapping("/virusTotal")
@Tag(name = "virusTotal", description = "Operations related to Virus Total API")
public class VirusTotalController {

    @Autowired
    private VirusTotalService virusTotalService;

    @Autowired
    private VirusTotalFileWriteGateWay virusTotalFileWriteGateWay;

    @GetMapping("/ip/{ip}")
    @Operation(summary = "Get IP information", description = "Fetches information about a given IP address.")
    public String getIpInfo(@PathVariable("ip") String ip, Model model) {

        Request request = virusTotalService.createGetRequest("ip_addresses/", ip, "");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        virusTotalFileWriteGateWay.writeToFile("ipInfo.txt", json);
        IpInfo ipInfoInstance = virusTotalService.getInstance(json, IpInfo.class);
        model.addAttribute("ipInfoInstance", ipInfoInstance);

        return "virus_total/ip_info";

    }

    @GetMapping("/ip/{ip}/comments")
    @Operation(summary = "Get IP comments information", description = "Fetches comments about a given IP address.")
    public String getCommentsOnIp(@PathVariable("ip") String ip, Model model) {

        Request request = virusTotalService.createGetRequest("ip_addresses/", ip, "/comments");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        IpComments ipCommentsInstance = virusTotalService.getInstance(json, IpComments.class);
        model.addAttribute("ipCommentsInstance", ipCommentsInstance);

        return "virus_total/ip_comments";
    }

    @GetMapping("/ip/{ip}/votes")
    @Operation(summary = "Get IP votes information", description = "Fetches votes about a given IP address.")
    public String getVotesOnIp(@PathVariable("ip") String ip, Model model) {

        Request request = virusTotalService.createGetRequest("ip_addresses/", ip, "/votes");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        IpVotes ipVotesInstance = virusTotalService.getInstance(json, IpVotes.class);
        model.addAttribute("ipVotesInstance", ipVotesInstance);
        model.addAttribute("json", json);

        return "virus_total/ip_votes";
    }

    @GetMapping("/domains/{domain}")
    @Operation(summary = "Get Domain information", description = "Fetches information about a given Domain.")
    public String getDomainInfo(@PathVariable("domain") String domain, Model model) {

        Request request = virusTotalService.createGetRequest("domains/", domain, "");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        DomainInfo domainInfoInstance = virusTotalService.getInstance(json, DomainInfo.class);
        model.addAttribute("domainInfoInstance", domainInfoInstance);

        return "virus_total/domain_info";

    }

    @GetMapping("/domains/{domain}/comments")
    @Operation(summary = "Get Domain comments", description = "Fetches comments about a given Domain.")
    public String getCommentsOnDomain(@PathVariable("domain") String domain, Model model) {

        Request request = virusTotalService.createGetRequest("domains/", domain, "/comments");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        DomainComments domainCommentsInstance = virusTotalService.getInstance(json, DomainComments.class);
        model.addAttribute("domainCommentsInstance", domainCommentsInstance);

        return "virus_total/domain_comments";
    }

    @GetMapping("/domains/{domain}/votes")
    @Operation(summary = "Get Domain votes", description = "Fetches votes about a given Domain.")
    public String getVotesOnDomain(@PathVariable("domain") String domain, Model model) {

        Request request = virusTotalService.createGetRequest("domains/", domain, "/votes");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        DomainVotes domainVotesInstance = virusTotalService.getInstance(json, DomainVotes.class);
        model.addAttribute("domainVotesInstance", domainVotesInstance);

        return "virus_total/domain_votes";
    }

    @GetMapping("/upload")
    @Operation(summary = "Get path for Upload File")
    public String fileUpload() {
        return "virus_total/upload_file";
    }

    @PostMapping("/upload")
    @Operation(summary = "Post path for Upload File", description = "Information about the analysis of the uploaded file.")
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
            postRequest = virusTotalService.createOtherPostRequest(dataUploadInstance.getData(), file);
        }

        postJson = virusTotalService.createResponseString(postRequest).orElseThrow();
        uploadFileInstance = virusTotalService.getInstance(postJson, UploadFile.class);

        Request getRequest = virusTotalService.createGetRequest( "analyses/", uploadFileInstance.getData().getId(), "");
        String getJson = virusTotalService.createResponseString(getRequest).orElseThrow();
        analysesFileInstance = virusTotalService.getInstance(getJson, AnalysesFile.class);
        model.addAttribute("analysesFileInstance", analysesFileInstance);

        return "virus_total/analyses_file";
    }


    /**
     * В случае, если статус по загруженному файлу - queued,
     * необходимо через 1-2 минуты, отправить этот запрос, где {id} - ID, присвоенный файлу на сервере Virus Total
     * @param id
     * @param model
     * @return
     */

    @GetMapping("/analyses/{id}")
    @Operation(summary = "Get information about the analysis of the uploaded file", description = "Information about the analysis of the uploaded file by id.")
    public String getResponse(@PathVariable("id") String id, Model model) {

        Request request = virusTotalService.createGetRequest("analyses/", id, "");
        String json = virusTotalService.createResponseString(request).orElseThrow();
        AnalysesFile analysesFileInstance = virusTotalService.getInstance(json, AnalysesFile.class);
        model.addAttribute("analysesFileInstance", analysesFileInstance);

        return "virus_total/analyses_file";
    }
}