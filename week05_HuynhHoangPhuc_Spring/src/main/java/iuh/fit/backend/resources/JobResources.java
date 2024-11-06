package iuh.fit.backend.resources;

import iuh.fit.backend.services.JobServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/vi/jobs")
public class JobResources {
    @Autowired
    private JobServices services;
}