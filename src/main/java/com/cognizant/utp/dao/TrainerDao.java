package com.cognizant.utp.dao;

import java.util.List;

import com.cognizant.utp.models.Trainer;

public interface TrainerDao {
    long createTrainer(Trainer trainer);
    Trainer getTrainerById(long trainerId);
    List<Trainer> getAllTrainers();
    boolean updateTrainer(Trainer trainer);
    boolean deleteTrainer(long trainerId);
}