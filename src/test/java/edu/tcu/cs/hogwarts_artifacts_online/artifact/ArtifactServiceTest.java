package edu.tcu.cs.hogwarts_artifacts_online.artifact;

import edu.tcu.cs.hogwarts_artifacts_online.wizard.Wizard;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {
    @Mock
    ArtifactRepository artifactRepository;
    @InjectMocks
    ArtifactService artifactService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // Given. Arrange inputs  and targets. Define the behavior of Mock object artifactRepository
         Artifact a = new Artifact();
         a.setId("1250808601744904192");
         a.setName("Invisibility Cloak");
         a.setDescription("An invisibility cloak is used to make the wearer invisible.");
         a.setImageUrl("ImageUrl");

         Wizard w = new Wizard();
         w.setId(2);
         w.setName("Harry Potter");

         a.setOwner(w);
         given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a)); // Defines the behavior of the mock object

        // When. Act on the target behavior. When steps should cover the method to be tested.
         Artifact returnedArtifact = artifactService.findById("1250808601744904192");


        // Then. Assert expected outcome
        assert(returnedArtifact.getId().equals(a.getId()));
        assert(returnedArtifact.getName().equals(a.getName()));
        assert(returnedArtifact.getDescription().equals(a.getDescription()));
        assert(returnedArtifact.getImageUrl().equals(a.getImageUrl()));
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());
        // When
        Throwable thrown = catchThrowable(() -> {
           Artifact returnedArtifact = artifactService.findById("1250808601744904192");
        });
        // Then
        assertThat(thrown).isInstanceOf(ArtifactNotFoundException.class).hasMessage("Could not find artifact with Id 1250808601744904192 :(");
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }
}