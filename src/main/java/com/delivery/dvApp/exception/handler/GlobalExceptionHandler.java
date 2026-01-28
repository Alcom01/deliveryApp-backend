package com.delivery.dvApp.exception.handler;


import com.delivery.dvApp.exception.body.*;
import com.delivery.dvApp.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

/**
 * Global interceptor for exceptions across the dvApp.
 * <p>
 * This class uses {@link RestControllerAdvice} to provide centralized
 * error handling for all Controllers. It ensures that whenever a
 * custom business exception is thrown, the client receives a
 * structured JSON body and an appropriate HTTP status code.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles cases where a requested Restaurant does not exist.
     * @return 404 Not Found with RestaurantNotFoundBody.
     */
    @ExceptionHandler(value={RestaurantNotFoundException.class})
    public  ResponseEntity<Object> handleRestaurantNotFoundException(RestaurantNotFoundException ex){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        RestaurantNotFoundBody restaurantNotFoundBody = new RestaurantNotFoundBody(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(restaurantNotFoundBody,notFound);
    }

    /**
     * Handles cases where a specific menu Item cannot be found.
     * @return 404 Not Found with ItemNotFoundBody.
     */
    @ExceptionHandler(value={ItemNotFoundException.class})
    public ResponseEntity<Object> handleItemNotFoundException(ItemNotFoundException ex){
          HttpStatus notFound = HttpStatus.NOT_FOUND;
          ItemNotFoundBody itemNotFoundBody = new ItemNotFoundBody(
                  ex.getMessage(),
                  notFound,
                  ZonedDateTime.now()
          );
          return new ResponseEntity<>(itemNotFoundBody,notFound);
    }

    /**
     * Handles name collision conflicts (e.g., trying to create a restaurant name that exists).
     * @return 400 Bad Request with NameAlreadyExistsBody.
     */
    @ExceptionHandler(value={NameAlreadyExistsException.class})
    public ResponseEntity<Object> handleNameAlreadyExistsException(NameAlreadyExistsException ex){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        NameAlreadyExistsBody nameAlreadyExistsBody = new NameAlreadyExistsBody(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(nameAlreadyExistsBody,badRequest);
    }

    /**
     * Handles missing Customer records.
     * @return 404 Not Found with CustomerNotFoundBody.
     */
    @ExceptionHandler(value={CustomerNotFoundException.class})
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        CustomerNotFoundBody customerNotFoundBody = new CustomerNotFoundBody(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(customerNotFoundBody,notFound);
    }

    /**
     * Handles missing Courier records.
     * @return 404 Not Found with CourierNotFoundBody.
     */
    @ExceptionHandler(value={CourierNotFoundException.class})
    public ResponseEntity<Object> handleCourierNotFoundException(CourierNotFoundException ex){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        CourierNotFoundBody courierNotFoundBody= new CourierNotFoundBody(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(courierNotFoundBody,notFound);
    }

    /**
     * Handles queries for a specific Order ID that does not exist.
     * @return 404 Not Found with OrderNotFoundBody.
     */
    @ExceptionHandler(value={OrderNotFoundException.class})
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        OrderNotFoundBody orderNotFoundBody= new OrderNotFoundBody(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(orderNotFoundBody,notFound);
    }

    /**
     * Handles business logic errors where an Item does not belong to the requested Restaurant.
     * @return 400 Bad Request with ItemMisMatchBody.
     */
    @ExceptionHandler(value={ItemMisMatchException.class})
    public ResponseEntity<Object> handleItemMisMatchException(ItemMisMatchException ex){
        HttpStatus badRequest =  HttpStatus.BAD_REQUEST;
        ItemMisMatchBody itemMisMatchBody = new ItemMisMatchBody(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(itemMisMatchBody,badRequest);

    }

    /**
     * Handles illegal order lifecycle transitions (e.g., delivering an unpicked order).
     * @return 400 Bad Request with InvalidOrderStatusBody.
     */
    @ExceptionHandler(value={InvalidOrderStatusException.class})
    public ResponseEntity<Object> handleInvalidOrderStatusException(InvalidOrderStatusException ex){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        InvalidOrderStatusBody invalidOrderStatusBody = new InvalidOrderStatusBody(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(invalidOrderStatusBody,badRequest);
    }

    /**
     * Handles requests for historical data when no orders have been completed.
     * @return 404 Not Found with OrderHistoryNotFoundBody.
     */
    @ExceptionHandler(value={OrderHistoryNotFoundException.class})
    public ResponseEntity<Object> handleOrderHistoryNotFoundException(OrderHistoryNotFoundException ex){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        OrderHistoryNotFoundBody orderHistoryNotFoundBody = new OrderHistoryNotFoundBody(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(orderHistoryNotFoundBody,notFound);
    }

    /**
     * Handles cases where a search for active orders (pending delivery) returns empty.
     * @return 404 Not Found with ActiveOrderNotFoundBody.
     */
    @ExceptionHandler(value={ActiveOrderNotFoundException.class})
    public ResponseEntity<Object> handleActiveOrderNotFoundException(ActiveOrderNotFoundException ex){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ActiveOrderNotFoundBody activeOrderNotFoundBody = new ActiveOrderNotFoundBody(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(activeOrderNotFoundBody,notFound);
    }

    /**
     * Handles validation errors regarding item quantities (e.g., zero or negative).
     * @return 404 Not Found (or 400) with InvalidQuantityBody.
     */
    @ExceptionHandler(value={InvalidQuantityException.class})
    public ResponseEntity<Object> handleInvalidQuantityException(InvalidQuantityException ex){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
         InvalidQuantityBody invalidQuantityBody = new InvalidQuantityBody(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(invalidQuantityBody,notFound);
    }

    /**
     * Handles attempts to create an order that already exists or is duplicated.
     * @return 400 Bad Request with OrderExistsBody.
     */
    @ExceptionHandler(value={OrderExistsException.class})
    public ResponseEntity<Object> handleOrderExistsException(OrderExistsException ex){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        OrderExistsBody orderExistsBody = new OrderExistsBody(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(orderExistsBody,badRequest);
    }
}
