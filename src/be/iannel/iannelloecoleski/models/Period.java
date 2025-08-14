package be.iannel.iannelloecoleski.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import be.iannel.iannelloecoleski.DAO.BookingDAO;

public class Period {
	
	private int id;
	private Date startDate;
	private Date endDate;
	private boolean isVacation;
	private String name;
	
	private List<Booking> bookings;
	
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

    public void loadBookings(BookingDAO bookingDAO) {
        if (this.id <= 0) {
            throw new IllegalArgumentException("Id de période non valide, impossible de charger les bookings.");
        }

        List<Booking> loadedBookings = bookingDAO.getBookingsByPeriodId(this.id);

        for (Booking booking : loadedBookings) {
            booking.setPeriod(this);
            this.addBooking(booking);
        }
    }

}

