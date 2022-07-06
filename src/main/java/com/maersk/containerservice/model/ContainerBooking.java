package com.maersk.containerservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Table(value = "bookings")
public class ContainerBooking implements Serializable {

    @PrimaryKey ("BOOKING_ID")
    @CassandraType(type = CassandraType.Name.INT)
    private Integer Id;

    @Column("CONTAINER_TYPE")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    private String containerType;

    @Column("CONTAINER_ORIGIN")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    private String origin;

    @Column("CONTAINER_DESTINATION")
    @CassandraType(type = CassandraType.Name.VARCHAR)
    private String destination;

    @Column("CONTAINER_QUANTITY")
    @CassandraType(type = CassandraType.Name.INT )
    private int quantity;

    @Column("Booking_Date")
    @CassandraType(type = CassandraType.Name.DATE)
    private Date bookingDate;
    

}
