package com.example.TwitterBackend.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
   private String message;
   private boolean status;

}
