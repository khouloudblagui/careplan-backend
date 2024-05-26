package com.example.careplane.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Response {
    private String message;
    private boolean success;
    private String responseMessage;
    private String email;



   /* public boolean isSuccess() {

        return success;
    }*/
   public Response(String responseMessage, boolean success) {
       this.responseMessage = responseMessage;
       this.success = success;
   }
}
