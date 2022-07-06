package com.maersk.containerservice.repository;

import com.maersk.containerservice.model.BookingRequest;
import com.maersk.containerservice.model.ContainerBooking;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends CassandraRepository<ContainerBooking, Integer> {


    @Query ("SELECT MAX(booking_id) FROM container.bookings")
    public Integer findMaxBookingCounter();


}
