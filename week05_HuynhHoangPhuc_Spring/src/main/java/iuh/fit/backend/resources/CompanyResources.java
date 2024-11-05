package iuh.fit.backend.resources;

import iuh.fit.backend.services.CandidateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/vi")
public class CompanyResources {
    @Autowired
    private CompanyResources services;
}
