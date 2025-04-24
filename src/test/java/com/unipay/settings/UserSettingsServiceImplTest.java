package com.unipay.settings;

import com.unipay.command.UserSettingsCommand;
import com.unipay.models.UserSettings;
import com.unipay.repository.UserSettingsRepository;
import com.unipay.service.settings.UserSettingsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class UserSettingsServiceImplTest {

    @Mock
    private UserSettingsRepository userSettingsRepository;

    @InjectMocks
    private UserSettingsServiceImpl userSettingsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldValidateAndSaveUserSettings() {
        UserSettingsCommand command = mock(UserSettingsCommand.class);
        UserSettings settings = mock(UserSettings.class);

        mockStatic(UserSettings.class).when(() -> UserSettings.create(command)).thenReturn(settings);
        when(userSettingsRepository.save(settings)).thenReturn(settings);

        userSettingsService.create(command);

        verify(command).validate();
        verify(userSettingsRepository).save(settings);
    }
}