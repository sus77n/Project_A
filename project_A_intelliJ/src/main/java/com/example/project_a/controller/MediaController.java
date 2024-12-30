package com.example.project_a.controller;

import com.example.project_a.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MediaController {
    @Autowired
    MediaService mediaService;

}
