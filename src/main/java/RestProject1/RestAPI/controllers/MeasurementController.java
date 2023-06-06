package RestProject1.RestAPI.controllers;

import RestProject1.RestAPI.dto.MeasurementDTO;
import RestProject1.RestAPI.dto.MeasurementsResponse;
import RestProject1.RestAPI.models.Measurement;
import RestProject1.RestAPI.services.MeasurementService;
import RestProject1.RestAPI.util.ErrorResponse;
import RestProject1.RestAPI.util.MeasurementValidator;
import RestProject1.RestAPI.util.NotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping
    public MeasurementsResponse getAllMeasurements(){
        return new MeasurementsResponse(measurementService.all().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("rainyDaysCount")
    public Integer getValueRainyDays(){
        return measurementService.getAllRainingDays();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult){
        measurementValidator.validate(convertToMeasurement(measurementDTO),bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                stringBuilder.append("Error: ").append(error.getField()).append(" - ").append(error.getDefaultMessage());
            }

            throw new NotCreatedException(stringBuilder.toString());
        }

        measurementService.add(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }



    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotCreatedException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTime(System.currentTimeMillis());

        return  new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO,Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement,MeasurementDTO.class);
    }
}
