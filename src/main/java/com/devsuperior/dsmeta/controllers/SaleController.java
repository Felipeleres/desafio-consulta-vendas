package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(@RequestParam(value = "minDate",defaultValue ="") String minDate,
													  @RequestParam(value = "maxDate", defaultValue = "")String maxDate,
													  @RequestParam(value = "name",defaultValue = "") String name, Pageable pageable) {
		Page <SaleReportDTO> saleReport = service.generateReport(minDate,maxDate,name,pageable);
		return ResponseEntity.ok().body(saleReport);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<Page<SaleSummaryDTO>> getSummary(@RequestParam(value = "minDate",defaultValue = "") String minDate,
														   @RequestParam(value = "maxDate",defaultValue = "") String maxDate,Pageable pageable) {
		Page<SaleSummaryDTO> saleSummary = service.generateSummary(minDate,maxDate,pageable);
		return ResponseEntity.ok().body(saleSummary);
	}
}
