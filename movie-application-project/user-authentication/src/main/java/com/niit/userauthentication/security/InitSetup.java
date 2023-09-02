package com.niit.userauthentication.security;


import com.niit.userauthentication.domain.Image;
import com.niit.userauthentication.domain.Role;
import com.niit.userauthentication.domain.DatabaseUser;
import com.niit.userauthentication.repository.RoleRepository;
import com.niit.userauthentication.repository.DatabaseUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitSetup{

    private boolean alreadySetup = false;
    private final DatabaseUserRepository databaseUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        if(alreadySetup)
            return;

        this.createRoleIfNotFound("ROLE_ADMIN");
        this.createRoleIfNotFound("ROLE_USER");

        Role adminRole = this.roleRepository.findByName("ROLE_ADMIN");
        if(databaseUserRepository.findByEmail("test@test.com") == null) {
            DatabaseUser databaseUser = new DatabaseUser();
            databaseUser.setEmail("test@test.com");
            databaseUser.setPassword(passwordEncoder.encode("test"));
            databaseUser.setRoles(List.of(adminRole));
            databaseUser.setEnabled(true);
            databaseUser.setImage(new Image(new byte[]{-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, -52, 0, 0, 0, -52, 8, 3, 0, 0, 0, 8, -4, -68, -64, 0, 0, 0, 111, 80, 76, 84, 69, -1, -1, -1, -18, -18, -18, 0, 0, 0, -19, -19, -19, -20, -20, -20, -9, -9, -9, -10, -10, -10, -11, -11, -11, -4, -4, -4, -16, -16, -16, -3, -3, -3, -8, -8, -8, -15, -15, -15, -17, -17, -17, 123, 123, 123, 117, 117, 117, -86, -86, -86, -95, -95, -95, 103, 103, 103, -28, -28, -28, -64, -64, -64, -36, -36, -36, -118, -118, -118, -77, -77, -77, -107, -107, -107, -51, -51, -51, 69, 69, 69, 98, 98, 98, 40, 40, 40, 111, 111, 111, 49, 49, 49, 58, 58, 58, 84, 84, 84, 11, 11, 11, 29, 29, 29, 22, 22, 22, 76, 76, 76, -37, -71, 41, 48, 0, 0, 23, 2, 73, 68, 65, 84, 120, -100, -19, 93, -23, -106, -93, -84, 22, 37, 113, 0, 5, -115, -58, 57, -58, -52, -17, -1, -116, 23, 20, 14, 56, -92, -110, -88, 93, -87, -69, -42, -57, -81, 46, 90, 119, -50, 22, -124, 51, 34, 66, -78, -71, -101, -82, 121, -66, -20, -64, -35, -33, 22, 35, -78, -125, 90, 93, 79, -96, 58, 28, -69, -21, -80, -1, 8, -58, 6, -83, 0, -14, 31, -103, -1, -56, -4, 71, -26, 63, 50, 95, -57, 48, -56, -40, 93, 11, 0, 68, 118, 104, 16, -39, -31, 33, 0, -111, -19, -49, 96, -72, -86, 57, 93, -93, -125, -65, 29, 103, 120, -123, -125, -27, -33, -12, -39, 21, -33, -62, 64, 114, -124, 108, 71, -15, -12, -28, -80, 82, -7, 55, 97, -35, -80, 90, 88, 118, -8, -98, -20, 112, -43, 45, -10, 31, -63, 24, -111, 33, -63, 19, -112, -115, 6, -111, -9, 60, 21, 68, 97, -40, 10, 3, -31, -128, -73, 89, 24, -97, -56, -15, -81, -56, 88, 86, -32, 49, 62, -51, 125, 66, 28, -52, 60, -26, 97, -116, 3, -122, -23, 39, 24, 127, -123, -116, -121, 29, 106, 37, 73, -106, 22, 69, 81, -26, 97, -45, 52, 121, 25, 21, 85, -106, -32, -1, 59, 50, 124, 5, 74, -86, -94, -36, 95, 14, -9, -83, -39, 110, -121, -70, 44, -78, -32, -1, -117, 76, 86, -28, -5, 62, 13, -93, 61, -62, -126, 111, 8, -127, -11, 27, 100, -112, 92, 35, -20, 31, 64, 126, 92, -119, 88, -111, 95, 110, -49, -104, -76, -19, -66, -117, 25, -39, 88, 63, 96, -52, -108, 3, 121, 93, 11, 40, 34, -94, 33, -97, -55, 14, -89, -21, -16, 9, -106, -105, -72, 93, 7, 81, 87, 120, 88, 118, -96, 64, -34, -62, 81, -125, 120, 127, -4, -111, 73, -37, -82, 77, 74, -40, 52, -58, 2, 57, -8, 77, -78, -71, -98, 88, 61, -7, 18, 68, 100, 7, -18, 58, 2, -84, 58, 104, 32, 59, -44, 45, -114, -20, 8, 20, 8, 66, -47, -29, -25, 65, -127, 118, 46, 16, -102, -60, 88, 36, -121, 106, -82, 28, 51, -83, 19, -55, 14, 67, -115, -24, 58, -98, -21, 85, -43, -7, -23, -101, 50, 30, -100, -46, -103, -60, 88, 32, -57, -86, -118, 102, -7, 54, -109, -74, 21, -122, 100, 127, 77, 107, -50, -22, -47, -61, -65, 31, 79, -89, 75, 88, -58, 113, 84, 20, 113, -39, -100, 78, -73, -85, -7, -1, -89, 48, -62, 4, 7, 127, -112, 76, -11, -24, 51, 57, 30, -102, 50, -82, 108, -126, -116, 102, 71, 121, 61, 88, 30, -54, 10, -73, 116, -2, 18, 25, -65, -22, -115, -53, -19, 18, 22, 9, 127, 39, 124, -52, -72, 98, 99, -88, -17, -56, 46, -102, 62, -99, 91, -103, 49, -10, -73, -56, -12, -58, -27, -40, 68, -103, -60, 24, -66, -68, 14, -13, -67, -24, -36, 31, -100, 71, -54, 41, -81, 76, -58, 106, -101, 105, 20, 117, 29, -58, 42, 34, -81, 64, 0, 34, -17, 65, -39, 69, -53, 118, -33, 21, 9, 122, -114, 97, 99, 90, -35, -6, 108, -50, 17, 99, -58, 106, 54, 91, 14, 11, 97, -39, 28, -38, 53, 103, -44, -31, -54, 14, 87, 118, -48, 97, 7, -39, 24, 115, -20, -110, -38, -120, -4, -124, -31, 16, 84, 12, 86, -118, 91, -20, -116, 111, -7, 92, 14, 58, 75, -99, -111, 3, 15, 106, 68, -93, -27, -38, 101, -17, 96, 92, -122, 108, -118, 89, -22, -52, 80, -114, 53, 20, -51, -44, 88, -100, -100, -73, 48, -20, 60, -20, 47, 107, -9, 84, -78, -7, -74, -42, 76, -12, 11, 29, -67, -117, 65, 8, -75, -45, -67, 73, -121, -30, 63, 65, 38, 4, -127, -14, 15, 48, -124, 54, -110, -24, 91, -73, 53, -78, -2, 0, -103, -20, 4, 92, -4, -113, 48, 44, -37, 69, -87, 94, -40, -118, -106, -51, -105, -55, -108, 74, -71, -68, -64, -14, -6, 46, 6, 95, 76, 83, 120, 20, -121, -128, 45, 38, 35, -105, 104, 0, -15, 3, -39, 97, -128, 116, -51, -104, 34, 93, 107, 65, 18, -75, 93, 30, 43, -105, 126, -116, 97, -93, 84, 45, 4, -41, -110, 44, -111, -93, 109, 76, 54, 71, 26, 56, 4, 63, -21, 112, -89, 58, 80, -92, 38, 74, -50, -16, 12, 12, -121, -58, 91, 24, 89, 103, -66, 28, -99, 113, -90, -2, -31, -54, -1, -63, 79, 59, -100, -119, 14, -41, -38, -87, 89, 82, -95, 89, 24, 110, -94, 118, -87, 99, -124, 102, -53, -47, 53, 24, 33, -87, 70, -104, 70, 81, -41, -122, 106, -124, 5, -118, -122, 99, -37, 78, 117, 80, 3, 67, 102, 98, -72, 48, -47, 118, -63, 92, 57, -108, 58, 3, 100, -28, -37, -12, -111, -126, 23, 16, 53, 73, 78, -23, 92, 12, -122, 115, -119, 113, -98, -115, 33, -17, 89, 70, -122, -39, 106, -89, 104, -84, -39, -126, -112, 66, -67, 118, -15, 87, -55, -72, 74, 93, -66, -57, -77, 49, 54, 44, 81, -86, 64, -72, -7, 38, 25, -84, -74, -119, 118, -106, -51, 20, -60, 34, -15, 29, 22, -111, -17, -111, -79, 2, -11, -54, 92, -126, 37, 100, -44, 35, -71, 22, -85, 25, 103, 47, 65, -92, -117, 71, 71, -84, -126, 64, 57, 100, 118, -77, 49, 56, 25, -68, 81, 38, 65, 73, 103, 98, 72, 50, 79, 45, 29, -24, 120, 110, 46, 81, 53, -37, -7, 43, 51, 23, 67, -4, 47, -24, -86, -95, 77, 102, 98, -76, 109, -92, -50, -96, -64, 30, -86, 17, -78, 3, -44, 8, 121, 5, 87, 35, 50, -71, -53, 28, 83, -79, -8, -49, -61, 16, 77, -87, 17, -121, 12, -49, -58, 16, 77, -114, -48, 60, 5, -81, -110, 111, -18, -87, 114, 103, 99, -120, -90, -10, -51, 123, -123, -65, -89, 53, 87, 106, -69, -53, -16, 34, 50, -107, -46, -99, 83, -120, -83, -3, 62, 25, -27, -103, 120, -32, 101, -22, -69, -93, 84, -17, 56, -8, 26, 25, 80, 102, -22, -91, -122, -107, 114, -17, -108, -10, -73, -56, 56, -96, 85, -19, -105, -110, 1, 29, 0, -4, -71, 43, -111, 121, -30, -30, 25, -57, -33, 53, -103, -90, -11, -83, -52, -63, -112, -126, 52, 19, 100, 62, -59, -32, 77, 5, 106, 28, 21, -70, -63, -78, -125, -54, -65, -119, -20, -16, 84, 112, -121, 48, 25, 15, -94, 76, -111, -39, -95, -51, 76, 12, -73, -117, 82, -63, 83, -79, -55, 92, 12, 1, -93, -116, 52, 21, 100, 99, -66, -20, 112, 85, -112, -115, 116, 61, 124, -61, 87, 87, -56, 72, 29, -79, -107, 12, 33, -102, -117, 65, 69, 71, 0, -95, -99, -58, 114, 102, 98, -120, 6, 35, 52, -114, 88, -55, 57, -5, 92, -115, -96, -32, 42, -54, 103, 99, -76, -86, -120, -115, 96, 37, -79, -23, 76, 12, -47, 52, 25, -39, -15, -127, -126, 71, -115, -111, -103, -117, 33, -55, 68, 87, 32, 51, 19, 99, 61, 50, -51, 90, 100, 46, -1, -111, 89, -105, -52, 126, 41, -103, -30, -5, 100, 44, -75, 8, -43, 75, -55, 0, -48, 74, 100, 94, -57, -33, -107, -39, -92, 110, -95, -101, 120, 64, -26, 115, -116, 1, -103, 102, -29, -52, -60, 104, -55, 56, -125, 52, 66, -4, 52, -117, 16, 122, 32, -115, -112, -88, -56, -52, -63, -10, 103, 98, 116, -41, -125, 117, -74, -37, -112, -103, 24, -94, 33, -107, -84, -87, 35, 86, -78, -61, 48, -118, -70, 102, 24, 69, 93, -21, -103, 0, -77, 49, -38, 54, 86, 103, 62, -57, 64, 127, -60, 56, 3, -83, 121, 117, 69, -13, 19, 50, -32, 105, -94, -21, -112, -55, -65, 102, 2, -72, 72, 57, 52, 110, 17, -79, -106, -112, -15, -63, 61, -13, 69, 50, -54, 6, -72, -26, -53, -56, 100, 42, 44, 26, -79, -17, -111, 113, 96, 73, 37, -77, 49, 68, 3, 31, -64, 55, 29, 26, 20, 54, 59, -113, -83, -31, -99, 57, 46, 36, 51, 90, -102, -125, -63, -110, -24, 123, -125, 37, 17, 25, 75, -94, -110, -30, -112, -71, -77, 49, 120, 83, -50, -26, 75, -58, 102, 99, -104, 13, 10, 10, 84, -121, 42, 40, -128, -83, -104, 14, -81, 112, 108, -84, 18, -128, -114, -59, 108, 12, 33, 47, -8, 51, -36, -39, 24, -94, 105, 50, 114, -52, 62, -117, 2, -88, -116, -71, -37, -126, -112, -58, 6, -37, 106, 49, -117, -55, -97, 8, 54, -119, 40, -32, -14, 40, 64, -28, 127, -111, 76, 64, 96, 57, -77, 23, -112, -7, 19, -15, 25, -37, 34, -123, 33, -57, 76, 65, 60, 29, 75, 76, -66, 74, -122, 66, -38, -36, -20, 120, -92, -27, 66, 26, 97, 57, -5, -127, -84, 51, 50, -23, 121, 49, 25, 63, -38, -82, 78, 70, 45, 120, -97, -44, 122, -79, 68, -27, 52, 108, 119, -10, 92, -116, 0, 114, -101, 68, -20, -4, 123, 53, 103, 58, -22, -67, 61, 87, 62, -98, -121, 81, -23, 124, -75, 48, -96, -33, -85, 57, 3, -1, 44, 87, 104, 50, 54, -77, 94, 44, 53, -78, 105, -75, 58, -13, -5, 53, 103, -112, 7, 123, -116, 49, -75, -26, -22, 102, -72, -124, 60, -83, -40, -6, -98, -42, -84, -126, 77, -73, 72, -8, 29, -26, 43, -102, -119, -54, -64, -7, -98, -91, -87, 67, 26, 15, -20, 45, -78, 103, -64, 59, -77, -1, 30, 25, 6, 33, -17, -91, -63, 38, -27, 76, 120, 36, 95, 35, -29, 73, 50, -41, 120, 24, 108, -6, 84, -112, 68, -110, 57, 103, -85, -110, -7, -96, -42, 11, -56, -16, -35, -50, -98, -119, 33, 5, -55, -108, 86, 4, 35, -13, -37, 53, 103, -26, 52, 11, 22, -43, -117, -95, 74, -7, -102, 19, -14, -91, -102, 51, 10, 26, 98, -115, 22, -43, -117, 109, 32, -40, -76, 11, -24, -105, 106, -50, 104, -96, -106, -26, 115, 54, 23, -93, -43, -85, 44, -19, -47, 100, 111, -8, -102, -1, 77, -51, 25, 86, -77, -29, -104, -50, -58, 104, 5, -127, 74, -121, 28, 59, 115, 49, 22, -109, 81, 6, -64, 53, 95, 66, -122, -21, -34, 42, -25, -76, -8, -98, 9, -32, 65, -76, -23, -112, -51, -59, -80, -68, -64, 35, -71, 28, -31, 83, -10, -59, 76, 64, -84, -62, 119, -41, 124, 67, 49, -98, 21, -80, -30, -38, 62, -24, -51, -95, -11, 23, 18, 78, -73, -73, 93, 89, -26, 89, -16, 49, 6, 41, -14, -78, -124, -126, -75, -43, 28, 26, 50, -119, -18, -93, 90, -81, 0, 107, 27, -128, -73, -44, -5, 24, 3, -101, 85, -124, -113, -22, -101, 53, 103, 14, -126, -116, -13, -10, -11, 37, -97, 98, -72, -40, 44, 35, -28, 107, -39, 119, 107, -50, 74, -93, 40, -74, -8, 24, 35, 8, 12, -53, -20, 80, 25, 54, -47, 87, 106, -50, -36, 122, 76, -26, 125, 12, -81, -46, 119, 31, 11, -6, -11, 98, -96, -20, -72, -128, 12, 54, -86, 28, 67, -22, 125, -99, 12, 74, 96, -90, 52, -97, 98, -40, -112, -101, -75, -67, -106, 104, 121, 101, -45, 114, 50, 70, -39, -27, -89, 24, 22, 82, 53, 94, 92, -69, -77, 22, -53, -79, 10, 25, -104, -8, -10, -121, 24, -40, -122, 85, 125, -107, 58, -51, 97, -110, -10, -100, 90, 47, -90, -68, 43, -27, 103, 24, 58, 51, -29, -102, 5, 43, -56, -79, -76, -26, -84, 51, -109, -44, -118, 118, 65, -2, 71, 24, 48, 65, 47, -85, -56, -79, -80, -26, 76, -42, 122, -127, -73, -72, -14, -67, 15, 48, -12, -4, -116, -23, 42, 114, -88, -74, -96, -42, -53, 70, -119, 90, -98, 67, 100, 127, -126, 1, 94, -26, 12, 4, -7, 94, -51, -103, 92, 60, 16, 86, 82, 29, -37, 60, -6, 119, 49, -32, 25, -20, 60, 16, -28, -5, 39, 53, 64, -22, 57, -33, 45, -84, -9, 49, 64, 21, 42, -16, 95, 58, 118, 2, 82, 44, 14, 9, 126, 27, -61, 86, 74, -22, 37, -7, 83, 100, 116, -64, 40, -25, 67, -13, 38, 70, -82, 7, 38, -8, 75, 100, -76, 41, 112, -26, -86, -17, 123, 24, 112, 75, -99, -83, 127, 32, -56, 75, -112, 103, -55, -47, 66, 16, -56, -94, -31, 47, 51, 11, -34, -63, -64, 16, 116, 43, -4, 96, 116, -68, -47, -17, -41, -100, -87, 14, 97, 23, 65, 45, -4, 45, -58, -12, 53, 6, 33, -15, 13, 6, -122, 116, 24, -117, -27, 88, 86, 115, 38, 27, -1, -37, 11, -32, 13, 56, 23, -44, 121, -123, -127, 88, -95, -106, -116, 46, 83, 5, -39, 43, -56, -79, -42, -55, 115, -106, -101, -127, 1, 124, -50, 51, -10, 2, 35, -53, -75, -35, -64, 20, 70, 79, 14, -60, 58, -105, -21, -26, 27, -57, -24, 5, 52, -46, 86, 90, -24, -2, -116, 65, -12, -127, 91, -25, 74, 99, 24, 114, 80, 12, 110, -15, 110, 74, -3, -18, -103, -128, 30, -43, -89, -107, 52, -60, -6, -103, -116, 118, -22, -28, 38, 6, -56, -111, -123, -11, -34, 108, -11, -82, 66, -65, 73, -58, -94, -103, 122, -91, -73, -69, -73, -55, -64, 33, 34, 125, 50, -23, -16, 44, 62, -2, 102, -3, -22, 105, -115, 22, -87, 62, 38, -93, -58, 101, 72, 38, 26, 30, 97, 119, -117, -26, -111, 121, -30, -30, 121, -29, -36, 88, -80, -24, 37, -103, -89, 24, -118, 76, -114, -24, 16, -93, 59, -15, 49, -36, 14, 91, 78, -15, 75, 57, -106, -44, -100, -87, 90, 47, -123, -79, 1, -69, 38, 36, -34, -113, 24, 29, -103, 107, -114, 70, 24, -83, 28, -56, -34, -115, -56, 52, -8, -33, -42, -100, -87, 90, -81, 0, 64, -44, 34, -107, -65, -64, 32, -30, -55, -33, 74, -118, 72, -121, -63, 0, -93, -107, -61, -55, 30, 35, 50, 117, -94, -82, 104, 49, 2, -74, 114, -51, -39, -24, -92, 69, -125, -52, -113, 24, 22, -39, 9, 107, 1, 119, 24, -42, -122, 5, -82, -87, -50, 88, 78, 58, -30, -46, 37, -76, 1, -122, -27, 110, -16, -70, 53, 103, 115, -56, 88, -118, -52, 53, 15, 36, -122, 69, -78, -78, -126, 32, -77, -112, -125, 76, -112, -71, -127, -17, -105, 114, 35, -109, -40, 121, -118, -76, -91, -7, 101, 50, -41, 50, 80, 24, -92, -86, -73, -113, -46, -112, -61, 99, -54, -42, 59, 20, 105, 97, 102, -25, -55, -111, 97, 36, 109, -28, 73, 23, 127, -128, 12, -35, -27, 114, 77, 114, 44, -89, -53, -64, 13, 19, -112, -125, -63, -71, 58, 13, 95, -8, 116, -82, -127, -6, 89, 76, 98, -95, -43, -98, 21, -101, -17, -110, -39, -32, 84, 5, 87, 28, -102, 72, 63, -43, 3, -126, -68, 56, 49, 67, -91, 80, 62, 9, -15, -104, 36, -20, 118, 33, -59, -26, -53, 100, 54, -38, 43, 4, 7, 87, 109, 111, -7, 70, -114, 76, -91, -50, 58, 16, -121, -65, -59, -86, -124, 83, 29, -101, -88, -109, -19, -92, 94, -73, 74, -51, -39, 115, 50, 23, -9, 39, -116, -64, -64, -80, -51, -29, 1, 47, -103, 32, -61, -32, -3, 63, -118, -80, -81, 50, -108, -22, 46, -41, -128, 68, 70, 64, -24, 88, -11, -55, 44, -88, 57, 27, 93, 1, -119, 22, -36, 122, -28, 106, -18, 52, -122, 67, -110, -60, -111, 24, -56, 62, 109, 123, 45, 102, 92, -76, 66, 57, 72, -49, -124, 56, 72, -19, 57, -9, 48, -75, 125, -108, 52, -67, -21, 57, 27, -70, 86, -51, -103, 122, -68, 42, -31, 51, -46, 63, 115, -87, 44, 28, 76, 97, -32, -116, 79, -104, 22, -61, -94, -55, -16, -40, -58, 109, -109, -123, 90, -57, 60, 83, -53, 102, -127, -114, 102, 29, -118, 106, -88, -128, -98, 42, -6, -113, -114, -46, 55, -55, -16, 55, 86, -97, 60, 43, 49, 44, -113, 56, 85, -66, 77, 37, -122, -109, -116, 55, -6, 94, -37, -5, 124, 66, 90, -93, 3, 122, 7, 99, 67, 126, -125, -52, -10, 24, 86, -66, -119, 97, -71, 52, 43, -49, -19, -22, 32, 60, -8, 100, 124, -114, -16, -96, -59, 92, 95, 13, 88, -13, -13, 69, 71, 80, 13, 86, 38, 19, 15, 126, -24, -112, 39, -128, 97, 97, -66, -45, 95, -124, -57, -81, -61, 32, -58, -39, -69, -57, -23, 33, 106, -53, -14, 72, 57, 117, -94, -11, 89, -49, 55, -67, 123, -82, 73, -58, 67, 99, -103, 46, 49, -18, -34, 25, -49, -89, 113, -5, -122, 20, 29, 6, 51, -57, 37, -54, -14, -47, -99, -68, 9, -57, -75, -123, -118, 9, 50, 117, -107, -22, 69, 13, 118, -49, 53, -115, -77, -47, -103, -78, -94, -35, -21, -62, -27, 24, 4, -91, 117, 43, -44, -66, 93, -56, 125, 22, 104, 55, -64, 49, 114, 16, 46, -58, -25, -68, -41, -114, 56, -96, -110, 36, -27, 97, -8, 63, 98, -64, 117, -100, -6, 26, -83, 78, 38, 48, -114, -111, -20, -75, 91, -51, -97, 92, 37, 15, -91, -105, -55, 92, -66, -121, -11, -87, -56, 15, -79, -125, -32, -84, 119, -32, -23, -71, -84, 18, 11, 119, 114, 56, -101, 106, 103, 82, 61, 69, 66, 20, -3, -28, 26, 31, 4, 25, 46, -51, 51, 106, -67, -60, 31, -106, -79, 51, 15, -97, -28, 109, 7, -21, 109, -24, 41, 12, 18, -61, 68, 17, -118, -102, -115, -115, -12, -11, 115, -31, -15, -35, -45, 50, -28, 96, -47, 96, -33, -57, 26, 30, 52, 110, 104, -117, 107, -67, -116, 121, -110, -95, 120, 122, -112, -8, -53, 74, 52, -122, 22, 94, -122, 52, -70, -76, -94, 123, 83, 9, 3, -72, 95, -40, -64, -1, -84, 26, 49, 79, -81, 57, -27, 16, 1, -71, -63, -51, 41, -22, -55, -47, -110, -111, 115, 103, -90, 110, 102, 27, 71, -49, 11, 29, 11, -121, -109, 116, -102, 4, 107, -116, 0, -74, -52, 99, -42, 13, 123, -112, -97, -50, -100, -118, -16, 67, -116, -27, -32, -49, -21, -80, 61, -57, -120, -40, -101, -64, 56, -23, -66, 89, 83, -47, -76, -8, -49, -40, -123, -79, -114, 93, 50, 42, -82, -88, -62, -63, -15, -33, -37, -42, -57, 98, 25, 24, 80, 107, -70, -35, 43, -51, -77, 74, -124, -15, -11, 76, -114, -92, -55, 90, 57, 72, 5, 43, -36, -47, 94, -113, -116, -75, -63, -60, 75, -51, 125, -19, -100, 98, -42, 98, -112, 116, 55, 92, 84, 47, -119, 107, -110, 49, -10, 37, -16, 57, 17, -37, 122, 37, -121, -19, 24, -57, -112, -89, -21, 85, 54, 5, -114, -109, 20, 61, -65, -48, -79, -96, -98, -62, -32, 42, 99, -17, 67, 0, 124, -66, 7, 61, 12, -86, -67, 48, -103, -6, -39, -105, 33, 13, -57, -61, -6, 23, 67, 119, 45, 50, 1, -95, 85, -44, -41, 54, 110, 5, -10, -76, 25, -95, -125, 99, 109, 59, 85, -2, 0, -61, -122, -87, 120, -15, -98, -56, 65, 112, -5, 65, 20, 67, 14, -73, -128, -23, 41, -116, -122, 117, -56, 32, 90, -107, -113, -2, 76, 106, -113, -59, -41, 24, 105, -1, -75, 57, -112, 17, 70, 10, -9, -25, 61, 57, -60, -5, 78, -78, 74, 52, -66, 65, 10, 109, 8, -28, 48, -50, -19, -65, 23, -2, 52, -103, -113, 107, -67, 112, -76, 63, -9, 30, -4, 86, -24, 42, 27, 3, 35, 27, 104, -8, -7, -72, 94, -52, -123, 25, 115, -54, 12, 57, 2, -126, -85, -80, 62, -100, 69, -69, -20, -46, -128, -21, 67, -102, -116, -42, 111, 66, -23, -31, 89, 82, 115, 38, 106, -87, -78, -14, 116, -37, -114, -38, -127, 34, -123, -127, -55, -48, 47, 121, -81, -48, -80, 94, -52, 113, 96, -33, -33, 35, -3, 43, 4, 85, 23, 99, -49, -65, -119, 15, -68, 40, 57, -8, -126, 3, -72, -91, 75, -105, -42, -100, 97, 31, -89, 35, 117, 73, -74, 11, -107, 24, 22, 99, 67, -27, -15, -104, 120, 67, 63, 113, -112, -87, -1, 124, 116, -114, -15, 78, -114, 100, 63, -72, 85, -8, 0, -75, 28, 70, 52, -40, -75, -105, -91, 53, -46, 4, -62, -111, -29, 118, 45, 37, 70, 64, 71, 106, 103, -115, 6, 78, -17, 32, -48, 90, 64, 69, 108, -112, -93, -102, 120, 84, -41, -120, 48, -112, 3, -26, 38, 95, 58, -125, 5, 33, 13, 43, -101, 48, -106, -114, 15, 88, 95, -60, -111, -70, 2, -61, -87, -122, -17, -45, 54, 66, -38, -12, -58, 12, 51, 15, 51, -56, 55, 59, 24, -7, 102, 19, -2, 76, -47, 74, -113, 41, 57, 64, 117, 106, 50, -90, -115, -13, 79, -55, -40, -23, -56, 106, -25, -125, -35, 20, -40, 40, -40, 76, -123, 58, -30, 36, 99, 117, -58, -90, 90, 89, -75, -109, -84, 74, -117, 16, -122, -96, -110, -21, 28, -105, -93, 26, -35, 40, 91, -52, 60, 41, 7, 3, 91, -24, 16, 22, 69, -107, 36, -42, -89, 100, 8, -55, -118, -47, 92, 22, 95, 103, -87, 2, 31, 91, 58, 37, -8, 82, -95, 0, 7, -29, 43, -73, 124, -42, 11, 50, 73, -107, 114, 26, -11, -93, -89, -43, -85, -67, -46, 38, -55, -77, -41, 81, -100, -78, -87, 30, -86, -103, -128, -68, 61, 94, -22, 48, 74, 83, -66, -122, -65, 73, -58, -62, 52, -117, -57, -93, 114, 107, -65, -50, -62, -107, 41, -100, -124, -128, -65, 79, 8, 25, 71, -117, -74, 55, -82, -105, 97, 17, 104, 26, -21, 108, -37, -99, -74, 86, -51, -89, 112, 60, 60, -52, 1, 62, 39, 76, -110, 25, 121, 105, -60, -1, -18, -33, 75, -46, -74, 60, 100, 71, -11, -56, 124, 61, -28, 105, 107, -29, 11, 29, 13, 27, 102, 112, 110, 68, -109, 79, 32, 77, -51, -81, -61, -125, -124, 121, -43, -46, 113, 40, -111, -65, 16, 69, 85, 101, 85, 97, 104, 17, 109, -50, 84, 59, 85, 39, 125, 33, -17, -43, -100, 97, 110, -12, -34, 6, 119, 94, 15, -111, 80, -36, -119, -6, 70, 25, -47, 58, -16, 125, 7, 23, 95, -45, 76, 39, 112, 6, 30, 95, 120, -16, -60, 4, -28, -102, 89, 23, 48, 98, 88, -113, 126, 97, 99, -111, -114, 111, -18, 3, -9, -52, -19, 106, -50, 70, -82, -109, -114, -52, 59, 53, 103, -36, 58, 25, 13, 107, 88, -39, 34, 86, -33, -43, 122, -75, 49, 56, -108, -114, -42, 47, -66, -126, 17, 56, 50, -96, 64, -94, 94, -116, -79, 9, 50, -73, -124, 117, -126, 24, -13, 71, 120, -41, -60, -61, -60, -116, 38, 26, 14, 117, 53, 103, -18, -28, 42, 1, -85, -38, 115, 95, -77, -113, 70, 91, -28, 49, 18, -119, 19, -93, -49, 122, -115, 29, 26, -71, -21, 89, 36, 3, -23, -8, 45, -127, 55, 65, -26, 108, -85, 76, 64, -67, -125, 32, 45, 7, 1, 54, 23, 36, 35, 103, 104, -22, 59, 119, -102, -116, 124, 121, -122, -118, -26, -122, -46, 114, -16, -60, 31, 105, -9, 62, -115, 29, -25, -61, 45, -88, -74, -103, -27, 41, -1, -86, 48, -35, -87, 53, 36, 115, 61, 62, -54, -52, 86, -55, 115, 80, 89, -48, 0, 40, 22, 63, 0, 10, 12, -110, 10, 47, -74, -77, 42, 126, 12, -66, 53, -10, -118, 76, -32, 36, -3, 119, -19, -8, 0, 15, -30, -120, 12, 25, 56, -50, 30, -103, -61, -41, 90, -27, -26, -52, 6, 100, -114, -25, 71, -98, 86, 66, -43, -91, -98, -62, 80, -89, 89, 111, 11, 108, -54, -127, 97, 104, 50, -91, -67, 123, 110, -85, 120, 6, 85, 26, 13, 19, -34, -97, -111, -15, 6, 90, -40, 105, 111, 56, 67, 71, 100, -80, 99, -101, -53, -18, -87, 34, 124, -107, -77, -44, -97, -42, -128, 76, -25, 116, 98, 92, -51, -74, 0, 67, 121, 120, -114, 21, 33, -122, 28, 1, 44, -127, 5, -104, 34, 124, -10, -55, 43, 84, -7, -19, 11, 50, 30, -19, -27, 125, 28, -101, 20, 19, 13, 50, -2, -82, -97, 77, -11, -110, 38, -50, 9, 115, -7, -28, 6, -19, 11, 79, -111, 25, 96, 40, 50, -121, -118, -10, -55, -88, 9, 92, 78, -40, 85, -103, -102, 107, 63, -110, 9, -120, -15, -79, 27, -2, 6, 68, 76, -8, 77, 126, -8, -128, -76, -59, -43, 75, -8, 4, 75, 78, -7, -53, 107, -84, 68, -109, 100, -60, 103, 90, -84, 49, -103, 71, -42, 39, -61, 126, 32, -29, -62, -70, -93, -55, -56, -28, 53, -77, -42, -85, -105, -113, 115, 42, 51, 42, -14, -41, -90, -66, 115, 6, 24, 124, -79, 82, 59, -64, 62, 17, 24, -122, 42, -48, -66, 29, -10, 6, -56, -20, -94, 52, -53, 2, -22, 83, 110, 104, 91, 3, 50, -89, -118, -102, 53, 103, 27, -40, -100, 34, 72, -46, 22, 95, -17, -12, -109, 44, 45, -75, 70, -15, 83, -51, 89, 79, 105, -72, 84, 14, -94, -50, 116, -26, -95, -119, -31, -53, -108, -64, -125, 72, 86, 116, -120, -50, 116, -38, -118, 14, 126, -117, -79, 105, -98, 47, 117, -109, 71, 85, 98, 59, 18, 67, 31, 14, 82, -8, 38, 40, -127, 26, -99, 10, -55, -97, -27, -26, 101, 26, -25, -11, -59, 84, 119, 126, 82, 103, 76, -107, 111, -105, 80, -19, -81, -107, -38, -37, -109, 79, -82, 110, 68, 12, -30, -111, -111, 22, -61, 48, 54, 51, -36, -22, 102, 67, 13, -32, 118, -72, 92, 82, -46, 97, -80, 68, -41, 5, 51, 83, -83, 2, -85, -128, 97, -87, 121, 39, -105, -53, 105, -72, 73, -1, -92, 104, 26, -65, 90, -72, -40, 122, 29, -97, 113, -44, -12, 75, -110, 77, -21, -12, 54, 84, 28, -2, -114, 56, -101, 41, 50, -19, -93, -62, 18, 3, -56, 95, 83, -75, 104, -75, 100, -44, 20, 57, 35, 101, 87, 77, -59, 64, -98, -110, -39, 16, 99, -81, 44, 80, -16, 78, -80, 73, 127, -22, -72, -77, 60, 44, 98, -70, -102, 34, -6, -108, -52, 54, -111, 9, 120, -6, -13, 90, -121, -124, -40, 64, 6, 36, 47, 59, 101, -107, -73, 41, -17, -17, 51, 50, -6, -100, 46, -15, 76, 91, -89, -22, -5, 100, 20, -122, -37, -53, 78, -118, -47, -28, 52, -109, -1, -41, 97, 48, -93, -44, 49, 105, 63, 42, 46, 54, 2, 61, 10, 21, -106, 114, 76, -21, 102, 79, -56, 48, 11, 84, -109, 107, -47, -11, 125, 76, 102, 67, 122, 31, 114, -35, 119, 100, -36, 41, 50, 7, 53, -87, -120, -95, -33, -19, 18, -116, 25, 75, -116, 37, -75, 1, 19, 96, -22, 19, -79, -73, 103, 100, 92, -80, -125, -81, 49, 6, -37, -5, 51, 50, -84, -17, 100, 57, 118, 100, 80, 26, -18, -21, -61, -7, 120, -21, 9, 82, 73, 85, -112, 33, 83, -29, -88, -101, -90, 54, 116, -118, -82, -96, -91, -51, -20, -22, 27, 120, 119, -82, 26, -43, 77, -12, -92, -26, 12, -21, -28, 1, -2, 124, 94, -43, 122, 13, 19, -84, 101, -67, -104, 51, 8, -91, -95, 0, 48, -78, -76, -120, -13, -26, 114, 6, 70, -71, 4, -79, -47, -40, 17, 2, 45, -94, 74, 14, -19, 114, -40, 94, 79, -11, -82, 44, -46, -42, -123, 56, 93, -21, -123, -63, 107, 120, -88, -56, -85, 90, -81, -74, 52, 107, 2, 3, 15, -26, -126, 71, -43, 45, 62, -30, 70, -97, -69, -31, -108, 52, 83, -119, -22, 12, -30, -17, 70, -37, 89, -72, -5, 89, 68, -32, 81, -17, -29, 40, 77, 90, -91, -108, 60, -85, 57, 67, 112, -44, -57, 45, 86, 23, 60, -81, -11, -14, -4, -55, -14, -80, -111, 119, 54, 69, 125, 12, -15, 44, 107, -29, 63, 59, 12, 76, -98, 124, 50, 57, -76, -69, 75, 28, 79, 107, 38, 109, 72, -48, 87, 79, 87, -51, -108, 65, -83, 23, 108, -36, 23, -49, 121, -93, -26, 108, 18, 3, -11, 20, -69, 109, -21, 26, 31, 97, -64, 48, -44, 10, -61, 102, 108, -46, 38, -34, -87, -97, 113, 60, -38, 59, 85, -19, 85, -51, 25, 124, -29, -18, 30, -93, 15, 82, -76, -6, -54, 42, 29, -91, 56, -80, 49, 6, 104, 25, 42, -2, 45, 82, -76, 88, 58, 114, -32, 28, 35, 16, -107, 58, -16, 9, -65, -50, 30, 125, 17, -46, 8, -44, -100, -68, 8, -89, -64, 76, 50, -55, -48, -125, 114, -53, 38, 78, 106, 0, 69, -12, 96, 3, -122, -121, -109, 65, 90, 70, -109, 81, 60, 17, -46, 104, -125, -18, -81, -56, -64, -71, 101, 33, -78, 103, -109, 25, -19, 107, -9, 98, -62, 22, -47, -49, -66, 50, 48, 24, -54, -76, 1, 121, 46, -71, 122, 105, -60, 103, -12, 10, 113, 121, -121, -116, 90, 100, -82, -119, -5, 73, 38, -32, 11, 50, -37, -61, 8, -125, -24, 1, 40, 123, 24, -19, -70, -97, 21, 101, 25, 85, -84, 117, 106, 104, 57, -120, -85, 87, -120, -100, -67, 36, 67, 67, -8, 117, 107, 54, 25, 60, 86, 5, -49, 25, 25, 96, 104, -79, -10, -109, 15, 68, 52, -68, 105, 79, 13, -46, 49, 77, 108, 107, -57, 73, 49, 77, -58, 8, -116, 98, 125, -98, -92, -75, 49, -56, -56, -4, 117, 88, -51, 100, 7, -112, -111, 29, -99, 32, -10, -40, -103, 123, -113, -119, 114, 104, 117, 55, -24, -63, -69, -85, -9, 123, 28, -96, -107, -66, 78, 35, -126, 71, 116, -52, -29, 94, -67, -88, 57, 35, 70, 97, 59, 55, -115, 32, -47, 30, -50, -35, 80, -55, -8, -125, 14, -66, -115, -56, 14, -15, 71, 54, -95, -41, -42, 54, -43, 38, -105, -125, 60, -83, -121, 70, -83, 32, -36, 32, -12, -35, -127, -27, 103, -44, -100, 57, -66, 72, -73, -27, -26, 35, 124, -31, 65, 64, -6, 63, -42, -100, 97, 120, 96, 101, 17, 69, 81, -84, 90, 36, -37, -45, -114, -56, -20, -56, 39, -44, 18, 72, 13, 19, -11, 98, 65, -81, 36, 40, 104, -53, -55, 104, -102, 106, 57, 6, 53, 103, -98, -25, 36, 81, 70, 59, -107, -56, -72, -107, -128, -24, 83, -118, -90, -5, 84, -95, 88, -36, 66, -87, -100, 113, 101, -43, -14, -75, 58, 124, -24, -20, 25, -78, -119, 78, -89, -110, 42, 57, -70, -23, -91, 67, 73, 52, 109, -74, 53, -41, 20, -60, -65, -103, -98, -60, -112, -96, 53, 89, 63, 51, -38, -19, -42, 107, -86, 60, -127, 43, -85, -114, 14, 27, -36, 82, -95, 121, 51, -65, -13, -50, 55, 109, 6, -42, -120, -116, 21, -19, -60, -60, 61, -107, 93, 114, 33, -8, -105, -70, -124, -31, 103, 100, -20, 73, 99, 97, -91, -42, 125, 63, -100, -109, -15, -4, -78, -41, -55, -33, 38, 117, -100, -26, -83, -114, 69, -20, -33, 54, -85, -93, -86, 28, -62, 16, -46, 15, -87, -125, -123, 53, 125, 78, -58, 122, -90, -23, -83, -47, 100, -110, -72, -17, -31, -76, -97, 102, -63, -43, 82, -3, -110, -35, 79, 97, -22, -96, -42, 110, 22, 100, -86, -8, 108, -6, 34, -49, -111, -112, 94, 107, -50, -70, 76, 96, 106, 100, -58, 117, 69, -21, -75, -68, 93, -107, 125, 28, 24, -37, -112, 72, -98, 27, -19, 74, -41, 125, 42, -110, 83, 44, -13, 108, 90, -43, -60, 39, 81, 13, -109, -12, -104, -2, 64, -26, 69, -14, -19, -78, -42, -2, -80, 31, 96, 51, -27, -95, -100, -40, -108, 120, 115, -104, 37, -14, -117, 111, -29, -1, 57, 101, 102, -8, 4, -54, 4, -58, 53, 103, -34, 116, -116, 109, -83, 118, 73, -112, -88, 23, 11, 70, 9, 28, -29, -106, -71, -94, -94, 108, 34, 60, -38, 111, 123, 23, 54, -70, 81, -51, 25, 87, 50, 118, 97, 24, -18, 84, 11, 85, 123, -39, 49, -70, 101, 18, 35, 99, -19, -81, -8, 104, -13, 74, 76, 97, -80, 77, -57, 64, -51, 86, 59, -18, -53, -102, 51, -11, 85, 27, 88, 42, -120, -54, 119, 65, -125, 43, -16, -80, 99, 116, 69, 15, -125, -127, 58, 51, -59, -58, -36, 106, 91, 107, 101, 122, 6, 26, -29, -110, -96, -97, -75, -26, -74, 67, 85, -14, 65, -62, -23, 102, -16, 0, 84, 21, 30, -104, 77, -114, 49, -70, -49, 49, 12, 69, -45, 27, -79, -87, 99, 99, -14, -75, 89, 91, 102, -18, -55, 99, 60, 74, 77, -14, -113, -118, -127, -34, 56, -104, 96, -128, 49, -88, 46, 61, -106, -119, -109, -92, -79, 10, -46, 63, -60, 37, -16, -2, 31, -21, 40, 75, -54, 91, -17, -122, -19, 46, -7, 87, -107, 77, 51, 48, 122, -85, -64, -127, 111, 46, 46, -93, -112, -65, 125, 22, 87, -88, 77, -17, 92, -120, -33, -95, -3, 124, 23, -63, -27, -17, -112, 33, -6, -56, -9, 107, -103, -8, -19, -24, 98, 101, 112, -97, -88, 97, 91, -55, -54, 38, -108, 24, 59, 82, -109, -104, 114, 124, -99, -116, -74, -76, 78, 80, 64, -57, -44, 7, 20, -123, -42, 5, -98, -124, 80, -55, -86, 21, -44, 125, -46, -109, 99, -35, -102, -77, 89, 24, 78, 55, -45, -10, 68, 99, -88, 84, 53, 81, 104, 10, -50, 55, -48, 89, 40, 113, 58, -2, 106, -81, -4, 55, 53, 103, -77, 48, -80, -49, 87, -24, 67, -118, 40, 96, 96, 95, 42, -111, -41, 60, -55, 84, 13, -64, 53, 2, 81, 49, 105, -105, -72, -67, -125, 6, -65, -78, 106, -51, -39, 92, 12, -101, -85, -11, 61, 12, -48, -120, -81, -80, -19, -100, 83, 106, 98, -32, 93, -115, 93, 19, -93, 109, 114, -124, 86, -87, 108, 90, 13, 99, 34, -81, -79, 86, -55, -22, 10, 35, 64, -1, -14, -92, -122, 21, 49, -58, 9, -89, -37, 29, -58, 47, 49, -2, 38, 25, 123, -92, 25, 92, -53, 95, 61, -35, 100, 77, -116, -79, -49, -19, -73, 15, 4, 89, 19, -61, 8, 38, -63, 46, -12, 127, 75, 102, 116, 84, -53, 33, -5, 63, 38, 51, -12, 32, -18, -33, -63, -8, 31, 92, -34, -117, -69, -91, -72, 101, -8, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126}));
            this.databaseUserRepository.save(databaseUser);
        }
        alreadySetup = true;
    }

    @Transactional
    private void createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
    }
}
