package be.iannel.iannelloecoleski.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.BookingDAO;
import be.iannel.iannelloecoleski.DAO.PeriodDAO;

public class Period {
	
	private int id;
	private Date startDate;
	private Date endDate;
	private boolean isVacation;
	private String name;
	
	private List<Booking> bookings;
	
    private static BookingDAO bookingDAO = new BookingDAO();
    private static PeriodDAO periodDAO = new PeriodDAO();
    
	public Period() {
		bookings = new ArrayList<>();

	}
	
	public Period(int id, Date startDate, Date endDate, boolean isVacation, String name) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isVacation = isVacation;
		this.name = name;

		bookings = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public boolean getIsVacation() {
		return isVacation;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIsVacation(boolean isVacation) {
		this.isVacation = isVacation;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setVacation(boolean isVacation) {
		this.isVacation = isVacation;
	}
	
	
	public boolean containsDate(LocalDate date) {
	    return !date.isBefore(startDate.toLocalDate()) && !date.isAfter(endDate.toLocalDate());
	}

	public boolean isVacationPeriod() {
	    return isVacation;
	}

	public static boolean isSchoolPeriod(LocalDate date, List<Period> periods) {
	    for (Period period : periods) {
	        if (period.isVacationPeriod() && period.containsDate(date)) {
	            return true;
	        }
	    }
	    return false;
	}

	
	public boolean addBooking(Booking booking) {
        if (booking != null && !bookings.contains(booking)) {
            bookings.add(booking);
            if (booking.getPeriod() != this) {
                booking.setPeriod(this);
            }
            return true;
        }
        return false;
    }

    public boolean removeBooking(Booking booking) {
        if (booking != null && bookings.contains(booking)) {
            bookings.remove(booking);
            if (booking.getPeriod() == this) {
                booking.setPeriod(null);
            }
            return true;
        }
        return false;
    }

    public void loadBookings() {
        if (this.id <= 0) {
            throw new IllegalArgumentException("Id de période non valide, impossible de charger les bookings.");
        }

        List<Booking> loadedBookings = bookingDAO.getBookingsByPeriodId(this.id);

        for (Booking booking : loadedBookings) {
            booking.setPeriod(this);
            this.addBooking(booking);
        }
    }

    public boolean addPeriod() {
        if (periodDAO == null) {
            throw new IllegalArgumentException("PeriodDAO est null");
        }
        return periodDAO.create(this);
    }

    public static Period getPeriodById(int id) {
        if (periodDAO == null) {
            throw new IllegalArgumentException("PeriodDAO est null");
        }
        Period period = periodDAO.read(id);
        if (period != null) {
            period.loadBookings();
        }
        return period;
    }

    public static List<Period> getAllPeriods() {
        if (periodDAO == null) {
            throw new IllegalArgumentException("PeriodDAO est null");
        }
        List<Period> periods = periodDAO.readAll();
        for (Period period : periods) {
            period.loadBookings();
        }
        return periods;
    }

    public boolean deletePeriodById(int id) {
        if (periodDAO == null) {
            System.out.println("Erreur : PeriodDAO non initialisé !");
            return false;
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Id plus petit ou égal à 0");
        }
        return periodDAO.delete(id);
    }
    
    @Override
    public String toString() {
        return name 
                + " (Du " + startDate + " au " + endDate + ")"
                + (isVacation ? " [Vacances]" : " [Semaine scolaire]");
    }

}

