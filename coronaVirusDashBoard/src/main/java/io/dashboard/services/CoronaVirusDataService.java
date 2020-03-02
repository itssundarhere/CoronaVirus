package io.dashboard.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import io.dashboard.model.*;
import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CoronaVirusDataService {

	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private List<LocationStats> currentStatus = new ArrayList<>();

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusData() throws IOException, InterruptedException {
		List<LocationStats> newStats = new ArrayList<>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.parse(new StringReader(response.body()));
		for (CSVRecord record : records) {
			LocationStats locStats = new LocationStats();
			locStats.setCountry(record.get("Province/State"));
			locStats.setState(record.get("Country/Region"));
			int latestCases = Integer.parseInt(record.get(record.size() - 1));
			locStats.setLatestReportedTotalCases(latestCases);
			int difcasesOfPreDay = Integer.parseInt(record.get(record.size() - 2));
			locStats.setDiffFromPrevDay(Math.abs(latestCases - difcasesOfPreDay));
			// System.out.println(locStats);
			newStats.add(locStats);
		}
		this.currentStatus = newStats;
	}

	public List<LocationStats> getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(List<LocationStats> currentStatus) {
		this.currentStatus = currentStatus;
	}
}
