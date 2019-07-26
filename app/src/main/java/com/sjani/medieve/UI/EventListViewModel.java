package com.sjani.medieve.UI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sjani.medieve.Models.Event;
import com.sjani.medieve.Models.User;
import com.sjani.medieve.Utils.DataRepository;

import java.util.List;

public class EventListViewModel extends ViewModel {
    private static final String TAG = EventListViewModel.class.getSimpleName();

    private DataRepository repository;

    public EventListViewModel(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets Users from APi and store it in database
     * @return List of Users
     */
    public LiveData<List<User>> getUsers() {
        return repository.getUserData();
    }

    /**
     * Gets Users from database
     * @return List of Users
     */
    public LiveData<List<User>> getUsersrfromDb() {
        return repository.getUsersFromDb();
    }

    /**
     * Gets Events from database
     * @return List of Events
     */
    public LiveData<List<Event>> getEventsforUser() {
        return repository.getEvents();
    }

    /**
     * Store event in database
     * @param event Event object
     */
    public void setEventinDb(Event event) {
        repository.setEvent(event);
    }

    /**
     * Gets new Event id based on database size
     * @return Id
     */
    public long getnewEventId() {
        return repository.getEventDbSize() + 1;
    }
}
