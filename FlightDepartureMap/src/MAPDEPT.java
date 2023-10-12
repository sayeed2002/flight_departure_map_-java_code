import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class MAPDEPT {
	private static final String CSV_FILE_PATH = "Flights.csv";
	private static final int UPDATE_INTERVAL_MINUTES = 5;

	static List<Flight> flights  ;
	static JFrame frame;
	static JTable table;
	static DefaultTableModel tableModel;
	static JLabel currentDateLabel;
	static JLabel currentTimeLabel;
	static JTable departuresTableBCL;





	public static void main(String[] args) {
		flights = new ArrayList<>();
		loadData();


		frame = new JFrame("BRUNEL CITY AIRPORT");
		frame.getContentPane().setLayout(null);
		tableModel = new DefaultTableModel();

		JLabel motto = new JLabel("Together, \nWe Make History");
		motto.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		motto.setBounds(102, 780, 229, 55);
		frame.getContentPane().add(motto);

		JLabel brunelLogo = new JLabel("");
		brunelLogo.setIcon(new ImageIcon(MAPDEPT.class.getResource("brunel-logo-blue-3.png")));
		brunelLogo.setBounds(6, 748, 84, 112);
		frame.getContentPane().add(brunelLogo);


		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);
		table.setForeground(new Color(0, 0, 0));
		table.setEnabled(false);
		table.setBackground(new Color(221, 237, 190));

		table.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 14));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(911, 0, 595, 499);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		frame.getContentPane().add(scrollPane);

		departuresTableBCL = new JTable();
		DefaultTableModel departuresTableModel = new DefaultTableModel();
		departuresTableModel.addColumn("Departure Time");
		departuresTableModel.addColumn("Arrival Airport");
		departuresTableModel.addColumn("Flight Number");
		departuresTableBCL.setModel(departuresTableModel);

		// Set the table properties
		departuresTableBCL.setFillsViewportHeight(true);
		departuresTableBCL.setEnabled(false);
		departuresTableBCL.setBackground(new Color(242, 245, 222));
		departuresTableBCL.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 14));

		// Create a scroll pane for the departures table
		JScrollPane departuresScrollPaneBCL = new JScrollPane(departuresTableBCL);
		departuresScrollPaneBCL.setBounds(6, 558, 1500, 178);
		departuresScrollPaneBCL.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		frame.getContentPane().add(departuresScrollPaneBCL);


		JLabel BCL = new JLabel("BCL");
		BCL.setForeground(Color.BLACK);
		BCL.setBounds(421, 114, 23, 16);
		AirportLabelMouseListener bcl = new AirportLabelMouseListener();
		BCL.addMouseListener(bcl);

		frame.getContentPane().add(BCL);
		JLabel GRU = new JLabel("GRU");
		AirportLabelMouseListener gru= new AirportLabelMouseListener();
		GRU.addMouseListener(gru);

		GRU.setBounds(272, 346, 33, 16);
		frame.getContentPane().add(GRU);

		JLabel LVS = new JLabel("LVS");
		AirportLabelMouseListener lvs = new AirportLabelMouseListener();
		LVS.addMouseListener(lvs);
		LVS.setBounds(74, 154, 33, 16);
		frame.getContentPane().add(LVS);

		JLabel DFW = new JLabel("DFW");
		AirportLabelMouseListener dfw = new AirportLabelMouseListener();
		DFW.addMouseListener(dfw);
		DFW.setBounds(136, 166, 33, 16);
		frame.getContentPane().add(DFW);

		JLabel JFK = new JLabel("JFK");
		AirportLabelMouseListener jfk = new AirportLabelMouseListener();
		JFK.addMouseListener(jfk);
		JFK.setBounds(212, 154, 23, 16);
		frame.getContentPane().add(JFK);

		JLabel MEX = new JLabel("MEX");
		AirportLabelMouseListener mex = new AirportLabelMouseListener();
		MEX.addMouseListener(mex);
		MEX.setBounds(102, 209, 33, 16);
		frame.getContentPane().add(MEX);

		JLabel DUB = new JLabel("DUB");
		AirportLabelMouseListener dub = new AirportLabelMouseListener();
		DUB.addMouseListener(dub);
		DUB.setBounds(381, 114, 33, 16);
		frame.getContentPane().add(DUB);

		JLabel LOS = new JLabel("LOS");
		AirportLabelMouseListener los = new AirportLabelMouseListener();
		LOS.addMouseListener(los);
		LOS.setBounds(421, 257, 33, 16);
		frame.getContentPane().add(LOS);

		JLabel JNB = new JLabel("JNB");
		AirportLabelMouseListener jnb = new AirportLabelMouseListener();
		JNB.addMouseListener(jnb);
		JNB.setBounds(493, 381, 23, 16);
		frame.getContentPane().add(JNB);

		JLabel DEL = new JLabel("DEL");
		AirportLabelMouseListener del = new AirportLabelMouseListener();
		DEL.addMouseListener(del);
		DEL.setBounds(655, 209, 33, 16);
		frame.getContentPane().add(DEL);

		JLabel HKG = new JLabel("HKG");
		AirportLabelMouseListener hkg = new AirportLabelMouseListener();
		HKG.addMouseListener(hkg);
		HKG.setBounds(759, 194, 33, 16);
		frame.getContentPane().add(HKG);

		JLabel SYD = new JLabel("SYD");
		AirportLabelMouseListener syd = new AirportLabelMouseListener();
		SYD.addMouseListener(syd);
		SYD.setBounds(838, 403, 33, 16);
		frame.getContentPane().add(SYD);

		JLabel BKK = new JLabel("BKK");
		AirportLabelMouseListener bkk = new AirportLabelMouseListener();
		BKK.addMouseListener(bkk);
		BKK.setBounds(735, 235, 23, 16);
		frame.getContentPane().add(BKK);

		JLabel DXB = new JLabel("DXB");
		AirportLabelMouseListener dxb = new AirportLabelMouseListener();
		DXB.addMouseListener(dxb);
		DXB.setBounds(560, 209, 33, 16);
		frame.getContentPane().add(DXB);

		JLabel CAI = new JLabel("CAI");
		AirportLabelMouseListener cai = new AirportLabelMouseListener();
		CAI.addMouseListener(cai);
		CAI.setBounds(503, 194, 33, 16);
		frame.getContentPane().add(CAI);

		JLabel RAK = new JLabel("RAK");
		AirportLabelMouseListener rak = new AirportLabelMouseListener();
		RAK.addMouseListener(rak);
		RAK.setBounds(360, 209, 33, 16);
		frame.getContentPane().add(RAK);

		JLabel SVO = new JLabel("SVO");
		AirportLabelMouseListener svo = new AirportLabelMouseListener();
		SVO.addMouseListener(svo);
		SVO.setBounds(520, 114, 33, 16);
		frame.getContentPane().add(SVO);

		JLabel ARN = new JLabel("ARN");
		AirportLabelMouseListener arn = new AirportLabelMouseListener();
		ARN.addMouseListener(arn);
		ARN.setBounds(447, 85, 33, 16);
		frame.getContentPane().add(ARN);

		JLabel CDG = new JLabel("CDG");
		AirportLabelMouseListener cdg = new AirportLabelMouseListener();
		CDG.addMouseListener(cdg);
		CDG.setBounds(401, 136, 33, 16);
		frame.getContentPane().add(CDG);

		JLabel LIS = new JLabel("LIS");
		AirportLabelMouseListener lis = new AirportLabelMouseListener();
		LIS.addMouseListener(lis);
		LIS.setBounds(391, 154, 23, 16);
		frame.getContentPane().add(LIS);

		JLabel MAD = new JLabel("MAD");
		AirportLabelMouseListener mad = new AirportLabelMouseListener();
		MAD.addMouseListener(mad);
		MAD.setBounds(447, 136, 33, 16);
		frame.getContentPane().add(MAD);



		JLabel AMS = new JLabel("AMS");
		AirportLabelMouseListener ams = new AirportLabelMouseListener();
		AMS.addMouseListener(ams);
		AMS.setBounds(451, 114, 33, 16);
		frame.getContentPane().add(AMS);

		JLabel ATH = new JLabel("ATH");
		AirportLabelMouseListener ath = new AirportLabelMouseListener();
		ATH.addMouseListener(ath);
		ATH.setBounds(493, 166, 33, 16);
		frame.getContentPane().add(ATH);

		JLabel FCO = new JLabel("FCO");
		AirportLabelMouseListener fco = new AirportLabelMouseListener();
		FCO.addMouseListener(fco);
		FCO.setBounds(462, 154, 33, 16);
		frame.getContentPane().add(FCO);

		JLabel HND = new JLabel("HND");
		HND.setForeground(new Color(8, 9, 22));
		AirportLabelMouseListener hnd = new AirportLabelMouseListener();
		HND.addMouseListener(hnd);
		HND.setBounds(819, 154, 33, 16);
		frame.getContentPane().add(HND);
		AirportLabelMouseListener yyz = new AirportLabelMouseListener();

		JLabel YYZ = new JLabel("YYZ");
		YYZ.addMouseListener(yyz);			
		YYZ.setBounds(176, 136, 33, 16);
		frame.getContentPane().add(YYZ);


		JLabel map = new JLabel("");
		map.setIcon(new ImageIcon(MAPDEPT.class.getResource("/map.png")));
		map.setBounds(6, 0, 903, 526);
		frame.getContentPane().add(map);
		frame.setSize(1512, 894);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		currentTimeLabel = new JLabel();
		currentTimeLabel.setForeground(new Color(229, 69, 39));
		currentTimeLabel.setBackground(new Color(128, 244, 75));
		currentTimeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		currentTimeLabel.setBounds(597, 6, 185, 16);
		frame.getContentPane().add(currentTimeLabel);

		JLabel lblNewLabel = new JLabel("Welcome to Brunel City London Airport");
		lblNewLabel.setForeground(new Color(185, 52, 35));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel.setBounds(6, 6, 347, 16);
		frame.getContentPane().add(lblNewLabel);

		currentDateLabel = new JLabel();
		currentDateLabel.setForeground(new Color(229, 69, 39));
		currentDateLabel.setBackground(new Color(128, 244, 75));
		currentDateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		currentDateLabel.setBounds(791, 6, 117, 16);
		frame.getContentPane().add(currentDateLabel);

		JLabel lblNewLabel_1 = new JLabel("ALL DEPARTURES FROM BCL AIRPORT TODAY");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_1.setForeground(new Color(30, 50, 168));
		lblNewLabel_1.setBounds(583, 538, 372, 16);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("1. Please Hover Over an Airport to see the Flights Information\n");
		lblNewLabel_2.setBounds(1081, 780, 425, 16);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("2. Double Click For Delayed Flights Information\n");
		lblNewLabel_3.setBounds(1081, 800, 412, 16);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("3. The Flights Showing in RED color are within next 30 Minutes\n");
		lblNewLabel_4.setBounds(1081, 819, 412, 16);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("INSTRUCTIONS:");
		lblNewLabel_5.setForeground(new Color(197, 48, 35));
		lblNewLabel_5.setBounds(1081, 761, 108, 16);
		frame.getContentPane().add(lblNewLabel_5);







		updateCurrentDateAndTimeLabels();  
		updateTableForAirport("");
		updateDeparturesTableForBCLAirport("BCL"); 
		startUpdateTimer();
	}




	static void updateCurrentDateAndTimeLabels() {
		LocalDateTime now = LocalDateTime.now();
		String formattedDate = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String formattedTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		currentDateLabel.setText("Date: " + formattedDate);
		currentTimeLabel.setText("Local Time(UK) : " + formattedTime);	}

	static void loadData() {
		try (BufferedReader br = new BufferedReader(new FileReader(getResourcePath(CSV_FILE_PATH)))) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			List<Flight> loadedFlights = new ArrayList<>();

			String line;
			while ((line = br.readLine()) != null) {
				String[] rowData = line.split(",");
				if (rowData.length >= 12) {
					LocalDateTime departureDateTime = LocalDateTime.parse(rowData[0] + " " + rowData[1], formatter);
					LocalDateTime arrivalDateTime = LocalDateTime.parse(rowData[0] + " " + rowData[2], formatter);
					String flightDuration = rowData[3];
					String distanceTravelled = rowData[4];
					int delay = Integer.parseInt(rowData[5]);
					String departureAirport = rowData[6];
					String departureCity = rowData[7];
					String arrivalAirport = rowData[8];
					String arrivalCity = rowData[9];
					String flightNumber = rowData[10];
					String airline = rowData[11];
					Flight flight = new Flight(departureDateTime, arrivalDateTime, flightDuration, distanceTravelled, delay, departureAirport, departureCity, arrivalAirport, arrivalCity, flightNumber, airline);
					loadedFlights.add(flight);
				}
			}

			flights = loadedFlights.stream()
					.sorted(Comparator.comparing(Flight::getDepartureDateTime))
					.collect(Collectors.toList());
		} catch (IOException | DateTimeParseException e) {
			e.printStackTrace();
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(null, "Flight Data Not Found " , "Error!", JOptionPane.ERROR_MESSAGE);
			});

		}
	}





	static String getResourcePath(String fileName) {
		return MAPDEPT.class.getClassLoader().getResource(fileName).getPath();
	}
	static void updateTableForAirport(String airportCode) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime endOfDay = now.with(LocalTime.MAX);

		tableModel.setRowCount(0);
		tableModel.setColumnCount(0);

		// Add specific columns to the table model
		tableModel.addColumn("Departure Time");
		tableModel.addColumn("Arrival Time");
		tableModel.addColumn("Delay");
		tableModel.addColumn("Departure City");
		tableModel.addColumn("Arrival City");
		tableModel.addColumn("Flight Number");

		for (Flight flight : flights) {
			LocalDateTime departureDateTime = flight.getDepartureDateTime();
			LocalDateTime arrivalDateTime = flight.getArrivalDateTime();
			if (flight.getDepartureAirport().equalsIgnoreCase(airportCode) &&
					departureDateTime.isAfter(now) && departureDateTime.isBefore(endOfDay)) {
				String departureTime = departureDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
				String arrivalTime = arrivalDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
				int delay = flight.getDelay();
				String departureCity = flight.getDepartureCity();
				String arrivalCity = flight.getArrivalCity();
				String airline = flight.getFlightNumber();

				// Check if flight is within 30 minutes
				boolean isWithin30Minutes = departureDateTime.isBefore(now.plusMinutes(30));

				// Create the row data array
				Object[] rowData = new Object[]{departureTime, arrivalTime, delay, departureCity, arrivalCity, airline};

				if (isWithin30Minutes) {
					// Apply red color to the row
					for (int i = 0; i < rowData.length; i++) {
						rowData[i] = "<html><font color='red'>" + rowData[i] + "</font></html>";
					}
				}

				tableModel.addRow(rowData);
			}
		}
		// Check if the table has no data

	}

	static void updateTableForDelayedFlights(String airportCode, int delayThreshold) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime endOfDay = now.with(LocalTime.MAX);

		tableModel.setRowCount(0);
		tableModel.setColumnCount(0);


		// Add specific columns to the table model
		tableModel.addColumn("Departure Time");
		tableModel.addColumn("Departure Airport");
		tableModel.addColumn("Arrival Airport");
		tableModel.addColumn("Original Arrival Time");
		tableModel.addColumn("Delay Time");
		tableModel.addColumn("New Arrival Time");
		tableModel.addColumn("Flight Number");


		for (Flight flight : flights) {
			LocalDateTime departureDateTime = flight.getDepartureDateTime();
			if (flight.getDepartureAirport().equalsIgnoreCase(airportCode) &&
					departureDateTime.isAfter(now) && departureDateTime.isBefore(endOfDay) &&
					flight.getDelay() >= delayThreshold) {
				String departureTime = departureDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
				String departureAirport = flight.getDepartureAirport();
				String arrivalAirport = flight.getArrivalAirport();
				LocalDateTime arrivalDateTime = flight.getArrivalDateTime();
				String originalArrivalTime = arrivalDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
				String airline = flight.getFlightNumber();
				int delay = flight.getDelay();

				// Calculate new arrival time
				LocalDateTime newArrivalDateTime = arrivalDateTime.plusMinutes(delay);
				String newArrivalTime = newArrivalDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
				Object[] rowData = new Object[]{departureTime, departureAirport, arrivalAirport, originalArrivalTime, delay, newArrivalTime,airline};
				tableModel.addRow(rowData);

			}
		}


	}

	static void updateDeparturesTableForBCLAirport(String airportCode) {
		LocalDate currentDate = LocalDate.now();
		LocalDateTime startOfDay = currentDate.atStartOfDay();
		LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

		DefaultTableModel model = (DefaultTableModel) departuresTableBCL.getModel();
		model.setRowCount(0);
		model.setColumnCount(0);

		// Add columns to the table model
		model.addColumn("Departure Time");
		model.addColumn("Departure Airport");
		model.addColumn("Arrival Airport");
		model.addColumn("Flight Number");
		model.addColumn("Departure City");
		model.addColumn("Arrival City");
		model.addColumn("Flight Duration");
		model.addColumn("Distance Travelled");
		model.addColumn("Delay");
		model.addColumn("Airline");

		for (Flight flight : flights) {
			LocalDateTime departureDateTime = flight.getDepartureDateTime();
			if (flight.getDepartureAirport().equalsIgnoreCase(airportCode) &&
					departureDateTime.isAfter(startOfDay) && departureDateTime.isBefore(endOfDay)) {
				String departureTime = departureDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
				String departureAirport = flight.getDepartureAirport();
				String arrivalAirport = flight.getArrivalAirport();
				String flightNumber = flight.getFlightNumber();
				String departureCity = flight.getDepartureCity();
				String arrivalCity = flight.getArrivalCity();
				String flightDuration = flight.getFlightDuration();
				String distanceTravelled = flight.getDistanceTravelled();
				int delay = flight.getDelay();
				String airline = flight.getAirline();

				model.addRow(new Object[]{departureTime, departureAirport, arrivalAirport, flightNumber, departureCity, arrivalCity, flightDuration, distanceTravelled, delay, airline});
			}
		}
	}



	static void startUpdateTimer() {
		TimerTask updateTask = new TimerTask() {
			@Override
			public void run() {
				loadData();
				updateTableForAirport(""); // Update table with no airport selected
				showUpdateMessage();
			}
		};
		Timer updateTimer = new Timer("FlightDataUpdateTimer");
		updateTimer.schedule(updateTask, UPDATE_INTERVAL_MINUTES * 60 * 1000, UPDATE_INTERVAL_MINUTES * 60 * 1000);

		// Create a separate TimerTask to update the time label every second
		TimerTask updateTimeTask = new TimerTask() {
			@Override
			public void run() {
				updateCurrentDateAndTimeLabels();
			}
		};

		Timer timeUpdateTimer = new Timer("TimeUpdateTimer");
		timeUpdateTimer.schedule(updateTimeTask, 0, 1000); // Update every second (1000 milliseconds)
	}


	static void showUpdateMessage() {
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(null, "Flight data updated successfully!", "Update", JOptionPane.INFORMATION_MESSAGE);
		});
	}




	public static List<Flight> getFlights() {
		
		return null;
	}




	public static DefaultTableModel getTableModel() {
		
		return null;
	}




	public static Object getDeparturesTableBCL() {
		
		return null;
	}
}

class Flight {
	LocalDateTime departureDateTime;
	LocalDateTime arrivalDateTime;
	String flightDuration;
	String distanceTravelled;
	int delay;
	String departureAirport;
	String departureCity;
	String arrivalAirport;
	String arrivalCity;
	String flightNumber;
	String airline;

	public Flight(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, String flightDuration, String distanceTravelled, int delay, String departureAirport, String departureCity, String arrivalAirport, String arrivalCity, String flightNumber, String airline) {
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
		this.flightDuration = flightDuration;
		this.distanceTravelled = distanceTravelled;
		this.delay = delay;
		this.departureAirport = departureAirport;
		this.departureCity = departureCity;
		this.arrivalAirport = arrivalAirport;
		this.arrivalCity = arrivalCity;
		this.flightNumber = flightNumber;
		this.airline = airline;
	}

	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}

	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}

	public String getFlightDuration() {
		return flightDuration;
	}

	public String getDistanceTravelled() {
		return distanceTravelled;
	}

	public int getDelay() {
		return delay;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public String getFlightNumber() {
		return flightNumber;
	}



	public String getAirline() {
		return airline;
	}


} 