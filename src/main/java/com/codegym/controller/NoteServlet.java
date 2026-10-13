package com.codegym.controller;

import com.codegym.dao.NoteTypeDAO;
import com.codegym.model.Note;
import com.codegym.service.NoteManagement;
import com.codegym.strategy.NoteDBStrategy;
import com.codegym.strategy.NoteFileStrategy;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/notes")
public class NoteServlet extends HttpServlet {
    private NoteManagement noteManagement;
    private NoteTypeDAO noteTypeDAO;

    @Override
    public void init() {
        noteManagement = new NoteManagement(new NoteDBStrategy());
        noteTypeDAO = new NoteTypeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String storage = req.getParameter("storage");
        if ("file".equals(storage)) noteManagement.setStrategy(new NoteFileStrategy());
        else if ("db".equals(storage)) noteManagement.setStrategy(new NoteDBStrategy());

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                showForm(req, resp, null);
                break;
            case "view":
                showView(req, resp);
                break;
            case "edit":
                showEdit(req, resp);
                break;
            case "delete":
                deleteNote(req, resp);
                break;
            default:
                listNotes(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("update".equals(action)) {
            noteManagement.updateNote(Integer.parseInt(req.getParameter("id")), req.getParameter("title"),
                    req.getParameter("content"), Integer.parseInt(req.getParameter("typeId")));
        } else {
            noteManagement.addNote(req.getParameter("title"), req.getParameter("content"),
                    Integer.parseInt(req.getParameter("typeId")));
        }
        resp.sendRedirect(req.getContextPath() + "/notes");
    }

    private void listNotes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        req.setAttribute("notes", noteManagement.searchNotes(keyword));
        req.setAttribute("keyword", keyword == null ? "" : keyword);
        req.getRequestDispatcher("/WEB-INF/views/note-list.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Note note) throws ServletException, IOException {
        req.setAttribute("note", note);
        req.setAttribute("types", noteTypeDAO.findAll());
        req.getRequestDispatcher("/WEB-INF/views/note-form.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showForm(req, resp, noteManagement.findById(Integer.parseInt(req.getParameter("id"))));
    }

    private void showView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("note", noteManagement.findById(Integer.parseInt(req.getParameter("id"))));
        req.getRequestDispatcher("/WEB-INF/views/note-view.jsp").forward(req, resp);
    }

    private void deleteNote(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        noteManagement.deleteNote(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/notes");
    }
}
