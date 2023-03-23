package org.project2.omwp2.document.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.document.service.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;




}
