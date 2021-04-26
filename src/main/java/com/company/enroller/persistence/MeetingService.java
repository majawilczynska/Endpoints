package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting findById(long id) {
		Meeting meeting = (Meeting) connector.getSession().get(Meeting.class, id);
		return meeting;
	}

	public void add(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
	}

	public void delete(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);
		transaction.commit();
	}

	public void update(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().merge(meeting);
		transaction.commit();
	}

	public Collection<Participant> getByIdMeeting(long id) {
		Meeting meeting = (Meeting) connector.getSession().get(Meeting.class, id);
		Collection<Participant> participants = meeting.getParticipants();
		return participants;
	}

	public void addParticipantToMeeting(long id, String login) {
		Meeting meeting = (Meeting) connector.getSession().get(Meeting.class, id);
		Participant participant = (Participant) connector.getSession().get(Participant.class, login);
		meeting.addParticipant(participant);
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();

	}

}
