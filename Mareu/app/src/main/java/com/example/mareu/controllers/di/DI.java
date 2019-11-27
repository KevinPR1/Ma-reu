package com.example.mareu.controllers.di;

import com.example.mareu.services.ImpMeetingApiService;
import com.example.mareu.services.MeetingApiService;

/**
 * Created by Kevin  - Openclassrooms on 13/11/2019
 */
public class DI {

    private static MeetingApiService sMeetingApiService = new ImpMeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     *
     * @return current implementation @{@link ImpMeetingApiService}
     */
    public static MeetingApiService getMeetingApiService() {
        return sMeetingApiService;
    }
}
