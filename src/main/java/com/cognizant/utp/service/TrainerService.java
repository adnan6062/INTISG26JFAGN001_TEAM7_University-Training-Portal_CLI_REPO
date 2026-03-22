package com.cognizant.utp.service;

import java.util.List;

import com.cognizant.utp.daoimpl.TrainerDaoImpl;
import com.cognizant.utp.models.Trainer;

public class TrainerService {

    private final TrainerDaoImpl trainerDao = new TrainerDaoImpl();

    public long createTrainer(Trainer trainer) {
        return trainerDao.createTrainer(trainer);
    }

    public Trainer getTrainerById(long trainerId) {
        return trainerDao.getTrainerById(trainerId);
    }

    public List<Trainer> getAllTrainers() {
        return trainerDao.getAllTrainers();
    }

    public boolean updateTrainer(Trainer trainer) {
        return trainerDao.updateTrainer(trainer);
    }

    public boolean deleteTrainer(long trainerId) {
        return trainerDao.deleteTrainer(trainerId);
    }
}