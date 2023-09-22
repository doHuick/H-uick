package com.huick.bankingservice.feign.contract.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.huick.bankingservice.domain.constant.ContractStatus;
import com.huick.bankingservice.domain.dto.ContractDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ContractApiDto {
    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Response {
        Long contractId;
        Long lesseeId;
        Long lessorId;
        LocalDateTime startDate;
        LocalDateTime dueDate;
        Long amount;
        Float rate;
        ContractStatus status;
        LocalDateTime createdTime;
        String pdfPath;

        @Builder
        public Response(Long contractId, Long lesseeId, Long lessorId, LocalDateTime startDate, LocalDateTime dueDate,
                        Long amount, Float rate, ContractStatus status, LocalDateTime createdTime, String pdfPath) {
            this.contractId = contractId;
            this.lesseeId = lesseeId;
            this.lessorId = lessorId;
            this.startDate = startDate;
            this.dueDate = dueDate;
            this.amount = amount;
            this.rate = rate;
            this.status = status;
            this.createdTime = createdTime;
            this.pdfPath = pdfPath;
        }

        public static Response from(ContractDto contractDto) {
            return Response.builder()
                    .contractId(contractDto.getContractId())
                    .lesseeId(contractDto.getLesseeId())
                    .lessorId(contractDto.getLessorId())
                    .startDate(contractDto.getStartDate())
                    .dueDate(contractDto.getDueDate())
                    .amount(contractDto.getAmount())
                    .rate(contractDto.getRate())
                    .status(contractDto.getStatus())
                    .createdTime(contractDto.getCreatedTime())
                    .pdfPath(contractDto.getPdfPath())
                    .build();
        }
    }
}
