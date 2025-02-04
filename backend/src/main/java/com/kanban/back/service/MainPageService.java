package com.kanban.back.service;

import com.kanban.back.dto.reponseDTO.mainpageDTO.BoardMainDTO;
import com.kanban.back.dto.reponseDTO.mainpageDTO.BoardUserMainDTO;
import com.kanban.back.dto.reponseDTO.mainpageDTO.TaskMainDTO;
import com.kanban.back.dto.reponseDTO.mainpageDTO.UserTableMainDTO;
import com.kanban.back.dto.requestDTO.BoardReqDTO;
import com.kanban.back.dto.requestDTO.CardReqDTO;
import com.kanban.back.dto.requestDTO.TaskReqDTO;
import com.kanban.back.entity.*;
import com.kanban.back.repository.BoardRepository;
import com.kanban.back.repository.CardRepository;
import com.kanban.back.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MainPageService {
    BoardRepository boardRepository;
    TaskRepository taskRepository;
    CardRepository cardRepository;

    @Autowired
    MainPageService(BoardRepository boardRepository, TaskRepository taskRepository, CardRepository cardRepository){
        this.boardRepository = boardRepository;
        this.taskRepository = taskRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public void createBoard(BoardReqDTO boardReqDTO){
        Board board = boardReqDTO.toEntity();
        boardRepository.save(board);
        // 나중에 여기에 조건문을 걸어서 repository가 연결 되었는지 확인
        defaultTask(board);
    }
    @Transactional
    public BoardMainDTO getBoard(String u_id){
        Board board = boardRepository.get_id_procedure(u_id);
        BoardMainDTO boardMainDTO = board.toMainDTO();

        // board에 속해있는 user정보 추가하는 코드
        List<UserTableMainDTO> userTables = new ArrayList<>();
            for (BoardUserMainDTO boardUserMainDTO : boardMainDTO.getBoardUsers()) {
                userTables.add(boardUserMainDTO.getUserTable().toMainDTO());
            }
            boardMainDTO.setUserTables(userTables);
        return boardMainDTO;
    }

    @Transactional
    public void updateBoard(BoardReqDTO boardReqDTO){
        Board board = boardRepository.getById(boardReqDTO.getB_id());
        board.update(boardReqDTO);
    }

    public void deleteBoard(Integer b_id){
        boardRepository.deleteById(b_id);
    }

    public void createTask(TaskReqDTO taskReqDTO){
        taskRepository.save(taskReqDTO.toEntity());
    }
    @Transactional
    public void updateTask(TaskReqDTO taskReqDTO){
        Task task = taskRepository.getById(taskReqDTO.getT_id());
        task.update(taskReqDTO);
    }

    public void deleteTask(Integer t_id){
        taskRepository.deleteById(t_id);
    }

    public void createCard(CardReqDTO cardReqDTO){
        Card card = cardReqDTO.toEntity();
        cardRepository.save(card);
    }

    @Transactional
    public void updateCard(CardReqDTO cardReqDTO){
        Card card = cardRepository.getById(cardReqDTO.getC_id());
        card.update(cardReqDTO);
    }

    public void deleteCard(Integer c_id){
        cardRepository.deleteById(c_id);
    }

    // 초기 task4개 만들기
    public void defaultTask(Board board) {
        int i = 1;
        List<String> names = Arrays.asList("Todo", "Doing", "Test", "Done");
        TaskReqDTO taskReqDTO = new TaskReqDTO();
        taskReqDTO.setBoard(board);
        taskReqDTO.setT_creator("admin");
        taskReqDTO.setT_type("git");
        taskReqDTO.setT_del_yn("no");

        for (String name : names) {
            taskReqDTO.setT_name(name);
            taskReqDTO.setT_position(i++);
            Task task = taskReqDTO.toEntity();
            taskRepository.save(task);
            }
        }
}
