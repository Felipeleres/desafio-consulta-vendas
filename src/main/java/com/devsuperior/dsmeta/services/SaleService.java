package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> generateReport(String minDate, String maxDate, String name, Pageable pageable){

		LocalDate dataMaxima;
		LocalDate dataMinima;

		if(maxDate.isBlank()){
			dataMaxima = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}else {
			dataMaxima = LocalDate.parse(maxDate);
		}

		if(minDate.isBlank()){
			dataMinima = dataMaxima.minusYears(1L);
		}else {
			dataMinima = LocalDate.parse(minDate);
		}

		Page <SaleReportDTO> saleReport = repository.generateSaleReport(dataMinima,dataMaxima,name,pageable);

		return saleReport;

	}

	public Page<SaleSummaryDTO> generateSummary(String minDate, String maxDate, Pageable pageable){

		LocalDate dataMaxima;
		LocalDate dataMinima;

		if(maxDate.isBlank()){
			dataMaxima = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}else {
			dataMaxima = LocalDate.parse(maxDate);
		}

		if(minDate.isBlank()){
			dataMinima = dataMaxima.minusYears(1L);
		}else {
			dataMinima = LocalDate.parse(minDate);
		}

		Page <SaleSummaryDTO> saleSummary = repository.generateSummaryReport(dataMinima,dataMaxima,pageable);

		return saleSummary;
	}

}
