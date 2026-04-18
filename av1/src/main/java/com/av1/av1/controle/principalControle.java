package com.av1.av1.controle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class principalControle {
    
    @GetMapping("/")
    public String Home() {
        return ". -- / ...- . .-. -.. .- -.. . --..-- / .--. .- .-. .- / -. ... / -. --- / . -..- .. ... - . / -.. . ... - .. -. --- .-.-.- / ... --- -- --- ... / .- --.- ..- . .-.. . ... / --.- ..- . --..-- / . -- -... . -... .. -.. --- ... / . -- / -- . -.. --- / . / .. --. -. --- .-. -. -.-. .. .- --..-- / .--. . .-. -.. . -- / --- / .--. .- ... ... --- / . / -.-. .- . -- / -. --- / .-. .. --- / .-.. .- -- .- -.-. . -. - --- / --.- ..- . / -.-. .... .- -- .- -- / -.. . / -.. . ... - .. -. --- .-.-.-";
    }
}