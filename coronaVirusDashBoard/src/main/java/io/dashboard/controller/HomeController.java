package io.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.dashboard.model.LocationStats;
import io.dashboard.services.CoronaVirusDataService;

@Controller
public class HomeController {
	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> currentStatus = coronaVirusDataService.getCurrentStatus();
		int totalReportedAsofToday = currentStatus.stream().mapToInt(stat -> stat.getLatestReportedTotalCases()).sum();
		System.out.println(totalReportedAsofToday+"Daily Report");
		model.addAttribute("LocationStatus", currentStatus);
		model.addAttribute("TotalReportedOnToday",totalReportedAsofToday);
		return "home";
	}
}
