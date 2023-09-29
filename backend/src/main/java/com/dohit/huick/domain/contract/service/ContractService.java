package com.dohit.huick.domain.contract.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.dohit.huick.domain.contract.constant.ContractStatus;
import com.dohit.huick.domain.contract.dto.ContractDto;
import com.dohit.huick.domain.contract.entity.Contract;
import com.dohit.huick.domain.contract.repository.ContractRepository;
import com.dohit.huick.domain.user.entity.User;
import com.dohit.huick.domain.user.repository.UserRepository;
import com.dohit.huick.global.error.ErrorCode;
import com.dohit.huick.global.error.exception.AuthenticationException;
import com.dohit.huick.global.error.exception.ContractException;
import com.dohit.huick.infra.aws.S3Uploader;
import com.itextpdf.html2pdf.HtmlConverter;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {
	private final ContractRepository contractRepository;
	private final SpringTemplateEngine templateEngine;
	private final S3Uploader s3Uploader;

	private static final String CONTRACT_S3_DIRNAME = "contract";
	private final UserRepository userRepository;

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public ContractDto createContract(ContractDto contractDto) {
		// 계약 만드는 사람에 따라 입력 정보 다름
		// ex) Render가 작성한다면 계약서에는 계약정보와 Render 개인정보가 입력되어야함.
		Contract contract = contractRepository.save(Contract.from(contractDto));

		return ContractDto.from(contract);
	}

	public ContractDto getContractByContractId(Long contractId) {
		return ContractDto.from(contractRepository.findByContractId(contractId).orElseThrow(() -> new ContractException(
			ErrorCode.NOT_EXIST_CONTRACT)));
	}

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public void updateContractStatus(Long contractId, ContractStatus contractStatus) {
		Contract contract = contractRepository.findByContractId(contractId).orElseThrow(() -> new ContractException(
			ErrorCode.NOT_EXIST_CONTRACT));
		contract.updateStatus(contractStatus);
	}

	public List<ContractDto> getContractByLesseeId(Long lesseeId) {
		return contractRepository.findByLesseeId(lesseeId).stream().map(ContractDto::from).collect(Collectors.toList());
	}

	public List<ContractDto> getContractByLessorId(Long lessorId) {
		return contractRepository.findByLessorId(lessorId).stream().map(ContractDto::from).collect(Collectors.toList());
	}

	public ContractDto updateFinalContract(Long contractId, ContractDto request) {
		Contract contract = contractRepository.findByContractId(contractId).orElseThrow(() -> new ContractException(
			ErrorCode.NOT_EXIST_CONTRACT));

		// request에 있는 정보들 모두 업데이트
		contract.updateByRequest(request);

		// 계약 정보를 HTML로 변환해주기
		User lessee = userRepository.findByUserId(request.getLesseeId()).orElseThrow(() -> new AuthenticationException(
			ErrorCode.NOT_EXIST_USER));
		User lessor = userRepository.findByUserId(request.getLessorId()).orElseThrow(() -> new AuthenticationException(
			ErrorCode.NOT_EXIST_USER));
		String htmlContract = contract2html(contract, lessee, lessor);

		// HTML문서를 PDF로 바꾸기
		byte[] pdfContract = html2pdf(htmlContract);

		// PDF S3에 저장하기
		String pdfS3Url = null;
		try {
			pdfS3Url = s3Uploader.uploadPdf(pdfContract, CONTRACT_S3_DIRNAME, contractId);
		} catch (IOException e) {
			throw new ContractException(ErrorCode.CANNOT_UPLOAD_PDF);
		}

		// PDF 저장 주소 contract에 저장해주기
		contract.updatePdfPath(pdfS3Url);
		contractRepository.save(contract);

		// 스마트 컨트랙트 생성을 위해서 계약 정보 리턴
		return ContractDto.from(contract);
	}

	private String contract2html(Contract contract, User lessee, User lessor) {
		// Thymeleaf를 사용하여 Contract 객체를 HTML 문자열로 변환
		Context context = new Context();
		// 계약정보
		context.setVariable("contract", contract);
		// 이자 갚을 날짜
		int paymentDay = contract.getCreatedTime().getDayOfMonth();
		context.setVariable("paymentDay", paymentDay);
		// 빌리는 사람 개인정보
		context.setVariable("lessee", lessee);
		// 빌려주는 사람 개인정보
		context.setVariable("lessor", lessor);

		return templateEngine.process("contract", context);
	}

	private byte[] html2pdf(String htmlContract) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			HtmlConverter.convertToPdf(htmlContract, outputStream);

			return outputStream.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert HTML to PDF", e);
		}
	}

	public List<ContractDto> getContractsByUserId(Long userId) {
		return contractRepository.findContractsByLessorIdOrLesseeId(userId, userId)
			.stream()
			.map(ContractDto::from)
			.collect(Collectors.toList());
	}
}