package org.example.inminute_demo.zoom.service;

import org.example.inminute_demo.zoom.domain.ZoomMeeting;
import org.example.inminute_demo.zoom.dto.request.ZoomMeetingDTO;
import org.example.inminute_demo.zoom.dto.request.ZoomMeetingSettingsDTO;
import org.example.inminute_demo.zoom.auth.ZoomTokenRepository;
import org.example.inminute_demo.zoom.repository.ZoomMeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class ZoomService {

    @Autowired
    private ZoomTokenRepository zoomTokenRepository;

    @Autowired
    private ZoomMeetingRepository zoomMeetingRepository;

    public ZoomMeeting createMeeting(ZoomMeetingDTO zoomMeetingDTO) throws IOException {
        // Token 만료 여부 확인
        // isExpired();

        System.out.println("Request to create a Zoom meeting");

        // API URL 설정
        String apiUrl = "https://api.zoom.us/v2/users/" + "swp0927@gmail.com" + "/meetings";

        // 호스트 이메일 설정
        zoomMeetingDTO.setHost_email("swp0927@gmail.com");

        // 미팅 설정
        ZoomMeetingSettingsDTO settingsDTO = new ZoomMeetingSettingsDTO();
        settingsDTO.setJoin_before_host(false);
        settingsDTO.setParticipant_video(true);
        settingsDTO.setHost_video(false);
        settingsDTO.setAuto_recording("local");
        settingsDTO.setMute_upon_entry(true);
        zoomMeetingDTO.setSettings(settingsDTO);

        // REST 요청 설정
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + zoomTokenRepository.findById(0L).get().getAccessToken());
        headers.add("content-type", "application/json");

        HttpEntity<ZoomMeetingDTO> httpEntity = new HttpEntity<>(zoomMeetingDTO, headers);
        ResponseEntity<ZoomMeetingDTO> zEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, ZoomMeetingDTO.class);

        // 응답 처리
        if (zEntity.getStatusCodeValue() == 201) {
            System.out.println("Zoom meeting response: " + zEntity);
            return zoomMeetingRepository.save(zEntity.getBody().toEntity());
        } else {
            System.out.println("Error while creating zoom meeting: " + zEntity.getStatusCode());
        }

        zoomMeetingDTO.setSettings(null);
        return null;
    }
}