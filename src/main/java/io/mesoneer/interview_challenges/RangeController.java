package io.mesoneer.interview_challenges;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RangeController {

    private final RangeService rangeService;

    public RangeController(RangeService rangeService) {
        this.rangeService = rangeService;
    }

    /**
     * using post request to send the range in string format in body
     * to avoid special characters in get request
     *
     * @param request
     * @return
     */
    @Operation(summary = "check if an input is in range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully get a boolean value"),
            @ApiResponse(responseCode = "500", description = "invalid input bound in request if message is: INVALID_BOUND_EXCEPTION"),
            @ApiResponse(responseCode = "500", description = "invalid class type in request if message is: CLASS_NOT_FOUND_EXCEPTION")
    })
    @PostMapping("/api/range")
    public ResponseEntity<Boolean> inRange(@RequestBody RangeRequest request){
        return new ResponseEntity<Boolean>(rangeService.inRange(request), HttpStatus.OK);
    }
}
