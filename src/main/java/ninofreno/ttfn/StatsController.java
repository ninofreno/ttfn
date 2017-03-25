package ninofreno.ttfn;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ninofreno.ttfn.transaction.Cache;
import ninofreno.ttfn.transaction.StatsDto;
import ninofreno.ttfn.transaction.TransactionDto;

@RestController
public class StatsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsController.class);

    @Autowired
    private Cache transactionCache;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<TransactionDto> transactions(@RequestBody final TransactionDto transaction) {

        LOGGER.debug("Received request at /transactions");
        try {
            transactionCache.update(transaction);
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/statistics")
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseBody
    public StatsDto statistics() {

        LOGGER.debug("Received request at /statistics");
        return transactionCache.getStatistics();
    }

    @RequestMapping("/")
    @ResponseBody
    public ResponseEntity<String> root() {

        LOGGER.debug("Received request at /");
        return new ResponseEntity<>("ninofreno rulez!", HttpStatus.OK);
    }

}
