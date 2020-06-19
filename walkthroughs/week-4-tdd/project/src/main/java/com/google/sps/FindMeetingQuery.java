// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.*;
import java.util.Collection;
import java.util.Collections;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

    ArrayList<Event> eventsList = new ArrayList<Event>();
    ArrayList<String> requestOptAttendeeList = new ArrayList<String>();
    ArrayList<String> requestAttendeeList = new ArrayList<String>();

    eventsList = sortList(events);
    requestOptAttendeeList = getAttendees(request, true);
    requestAttendeeList = getAttendees(request, false);

    Collections.sort(eventsList, new eventComparator());

    Collection meetingTimes = new ArrayList<TimeRange>();
    Collection meetingOptTimes = new ArrayList<TimeRange>();

    /* startTime(2) and endTime(2) are used to adjust the time range for each available meeting slot
    with duration being the length of the meeting */
    int startTime = 0;
    int startTime2 = 0;
    int endTime = 0;
    int endTime2 = 0;
    int endOfDayTime = 24*60 - 1;
    long duration = request.getDuration();

    for (int i = 0; i < eventsList.size(); i++){
      if (listSimilarity(requestAttendeeList, eventsList.get(i).getAttendees()) == true) {
        int meetingStartTime = eventsList.get(i).getWhen().start();
        int meetingDuration = eventsList.get(i).getWhen().duration();
        
        // Checks to see if there's any time left for a meeting
        if (meetingStartTime - startTime >= duration){
          endTime = meetingStartTime - 1;
          meetingTimes.add(TimeRange.fromStartEnd(startTime, endTime, true));
        }
        if(startTime < meetingStartTime + meetingDuration){ startTime = meetingStartTime + meetingDuration;}
        if (startTime == 1440){startTime--;}
      }
      if (listSimilarity(requestOptAttendeeList, eventsList.get(i).getAttendees()) == true
        || listSimilarity(requestAttendeeList, eventsList.get(i).getAttendees()) == true) {
        int meetingStartTime = eventsList.get(i).getWhen().start();
        int meetingDuration = eventsList.get(i).getWhen().duration();

        if (meetingStartTime - startTime2 >= duration){
            endTime2 = meetingStartTime - 1;
            meetingOptTimes.add(TimeRange.fromStartEnd(startTime2, endTime2, true));
        }
        if (startTime2 < meetingStartTime + meetingDuration){ startTime2 = meetingStartTime + meetingDuration;}
        if (startTime2 == 1440) {startTime2--;}
      }
    }
    // Checks to see if there's any more time slots left from startTime till the end of the day
    if ((endOfDayTime - startTime) >= duration && (startTime != endOfDayTime)) { 
      meetingTimes.add(TimeRange.fromStartEnd(startTime, endOfDayTime, true));
    }
    if (requestOptAttendeeList.size() > 0) {
      if ((endOfDayTime - startTime2) >= duration && (startTime2 != endOfDayTime)) { 
        meetingOptTimes.add(TimeRange.fromStartEnd(startTime2, endOfDayTime, true));
      }
    }
    if ((requestOptAttendeeList.size() > 0) && (meetingOptTimes.size() > 0)) { return meetingOptTimes;}
    return meetingTimes;
  }

// Used for finding similarities between then attendees of requested meeting and the events already set up
  public static boolean listSimilarity(ArrayList<String> a, Set<String> b){
    for(String attendee : a){
      for(String attendee2 : b){
        if (attendee.equals(attendee2)){return true;}
      }
    }
    return false;
  }

  public ArrayList<Event> sortList(Collection<Event> events) {
    ArrayList<Event> eventsList = new ArrayList<Event>();
      for (Event event : events) {
        eventsList.add(event);
      }

    Collections.sort(eventsList, new eventComparator());
    return eventsList;
  }

  public ArrayList<String> getAttendees(MeetingRequest request, boolean optional) {
    if (optional == true) {
      ArrayList<String> requestOptAttendeeList = new ArrayList<String>();
        for (String attendee : request.getOptionalAttendees()) {
          requestOptAttendeeList.add(attendee);
        }
    return requestOptAttendeeList;
    }
    ArrayList<String> requestAttendeeList = new ArrayList<String>();
    for (String attendee : request.getAttendees()) {
      requestAttendeeList.add(attendee);
    }
    return requestAttendeeList;
  }
}