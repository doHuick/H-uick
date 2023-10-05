package com.dohit.huick.domain.contract.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractService {
	private final ContractRepository contractRepository;
	private final SpringTemplateEngine templateEngine;
	private final S3Uploader s3Uploader;
	private final ResourceLoader resourceLoader;

	private static final String CONTRACT_S3_DIRNAME = "contract";
	private final UserRepository userRepository;

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public ContractDto createContract(ContractDto contractDto) {
		Contract contract = contractRepository.save(Contract.from(contractDto));
		return ContractDto.from(contract);
	}

	public ContractDto getContractByContractId(Long contractId) {
		Contract contract = contractRepository.findByContractId(contractId).orElseThrow(() -> new ContractException(
			ErrorCode.NOT_EXIST_CONTRACT));

		return ContractDto.from(contract);
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

	public ContractDto updateFinalContract(Long contractId, ContractDto request) throws IOException {
		Contract contract = contractRepository.findByContractId(contractId).orElseThrow(() -> new ContractException(
			ErrorCode.NOT_EXIST_CONTRACT));

		// request에 있는 정보들 모두 업데이트
		contract.updateByRequest(request);
		contract.updateStatus(request.getStatus());

		// 계약 정보를 HTML로 변환해주기
		User lessee = userRepository.findByUserId(request.getLesseeId()).orElseThrow(() -> new AuthenticationException(
			ErrorCode.NOT_EXIST_USER));
		User lessor = userRepository.findByUserId(request.getLessorId()).orElseThrow(() -> new AuthenticationException(
			ErrorCode.NOT_EXIST_USER));

		String htmlContract = contract2html(contract, lessee, lessor);

		// ########################### 추후 삭제예정 ###########################
		Files.write(Paths.get("output.html"), htmlContract.getBytes());

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
		// contractRepository.save(contract);

		// 스마트 컨트랙트 생성을 위해서 계약 정보 리턴
		return ContractDto.from(contract);
	}

	private String contract2html(Contract contract, User lessee, User lessor) {
		// Thymeleaf를 사용하여 Contract 객체를 HTML 문자열로 변환
		Context context = new Context();

		// 계약정보
		context.setVariable("contract", contract);

		// 빌리는 사람 개인정보
		context.setVariable("lessee", lessee);

		// 빌려주는 사람 개인정보
		context.setVariable("lessor", lessor);

		return templateEngine.process("contract", context);
	}

	private byte[] html2pdf(String htmlContract) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			// ConverterProperties 객체 생성
			ConverterProperties converterProperties = new ConverterProperties();

			// FontProvider 객체 생성
			FontProvider fontProvider = new FontProvider();

			Resource resource = resourceLoader.getResource("classpath:fonts/NanumMyeongjo-Regular.ttf");

			ClassPathResource classPathResource = new ClassPathResource("fonts/NanumMyeongjo-Regular.ttf");

			try {
				fontProvider.addFont("classpath:fonts/NanumMyeongjo-Regular.ttf");
				fontProvider.addFont(resource.getURL().getPath());
				fontProvider.addFont(classPathResource.getPath());
			} catch (Exception e) {
				throw new RuntimeException("Font file not found");
			}

			// ConverterProperties에 FontProvider 설정
			converterProperties.setFontProvider(fontProvider);

			// HTML을 PDF로 변환
			HtmlConverter.convertToPdf(htmlContract, outputStream, converterProperties);

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