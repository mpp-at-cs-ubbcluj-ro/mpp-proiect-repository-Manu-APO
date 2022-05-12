package competition;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import repository.TrialRepository;

@CrossOrigin
@RestController
@RequestMapping("competition/trials")
public class CompetitionTrialController {

    @Autowired
    private TrialRepository trialRepository;

    @GetMapping
    public Iterable<TrialDTO> getAll() {
        return trialRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        TrialDTO athleticTest = trialRepository.findOne(id);
        if (athleticTest == null) {
            return new ResponseEntity<String>("Trial test not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<TrialDTO>(athleticTest, HttpStatus.OK);
        }
    }

    @PostMapping
    public TrialDTO create(@RequestBody TrialDTO trial) {
        System.out.println("create");
        return trialRepository.add(trial);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            TrialDTO athleticTestReturned = trialRepository.delete(id);
            return new ResponseEntity<TrialDTO>(athleticTestReturned, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("{id}")
    public TrialDTO update(@RequestBody TrialDTO athleticTest, @PathVariable Long id) {
        if (id.equals(athleticTest.getId())) {
            return trialRepository.update(athleticTest.getId(), athleticTest);
        }
        return null;
    }


}
