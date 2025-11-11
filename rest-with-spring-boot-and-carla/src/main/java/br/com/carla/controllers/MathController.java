package br.com.carla.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    // http://localhost:8080/math/sum/8/2
    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("só é permitido número!");
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    // http://localhost:8080/math/subtraction/8/2
    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("Erro no subtraction");
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if(!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("Erro no multiplication");
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    @RequestMapping("/dividi/{numberOne}/{numberTwo}")
    public Double dividi(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("Erro no Dividi");
        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    ) throws Exception {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedOperationException("Erro no Mean");
        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
    }

    @RequestMapping("/squareroot/{number}")
    public Double squareRoot(
            @PathVariable("number") String number
    ) throws Exception {
        if (!isNumeric(number)) throw new UnsupportedOperationException("Erro no SquareRoot");
        return Math.sqrt(convertToDouble(number));
    }


    private Double convertToDouble(String strNumber) throws IllegalAccessException {
        if (strNumber == null || strNumber.isEmpty()) throw new IllegalAccessException();
        String number = strNumber.replace(",", ".");
        return Double.parseDouble(number);
    }

    private boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

}