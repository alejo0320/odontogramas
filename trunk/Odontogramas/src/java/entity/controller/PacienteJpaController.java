/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity.controller;

import conexion.jpaConnection;
import entity.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import entity.controller.exceptions.IllegalOrphanException;
import entity.controller.exceptions.NonexistentEntityException;
import entity.controller.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class PacienteJpaController implements Serializable {

    public PacienteJpaController() {
    }

    public EntityManager getEntityManager() {
        return jpaConnection.getEntityManager();
    }

    public void create(Paciente paciente) throws PreexistingEntityException, Exception {
        if (paciente.getMedicoList() == null) {
            paciente.setMedicoList(new ArrayList<Medico>());
        }
        if (paciente.getExamenfisicoestomatologicoList() == null) {
            paciente.setExamenfisicoestomatologicoList(new ArrayList<Examenfisicoestomatologico>());
        }
        if (paciente.getDiagnosticoList() == null) {
            paciente.setDiagnosticoList(new ArrayList<Diagnostico>());
        }
        if (paciente.getDatosconsultaList() == null) {
            paciente.setDatosconsultaList(new ArrayList<Datosconsulta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesiones profesionesCodigo = paciente.getProfesionesCodigo();
            if (profesionesCodigo != null) {
                profesionesCodigo = em.getReference(profesionesCodigo.getClass(), profesionesCodigo.getCodigo());
                paciente.setProfesionesCodigo(profesionesCodigo);
            }
            Municipios municipiosCodigo = paciente.getMunicipiosCodigo();
            if (municipiosCodigo != null) {
                municipiosCodigo = em.getReference(municipiosCodigo.getClass(), municipiosCodigo.getCodigo());
                paciente.setMunicipiosCodigo(municipiosCodigo);
            }
            List<Medico> attachedMedicoList = new ArrayList<Medico>();
            for (Medico medicoListMedicoToAttach : paciente.getMedicoList()) {
                medicoListMedicoToAttach = em.getReference(medicoListMedicoToAttach.getClass(), medicoListMedicoToAttach.getIdmedico());
                attachedMedicoList.add(medicoListMedicoToAttach);
            }
            paciente.setMedicoList(attachedMedicoList);
            List<Examenfisicoestomatologico> attachedExamenfisicoestomatologicoList = new ArrayList<Examenfisicoestomatologico>();
            for (Examenfisicoestomatologico examenfisicoestomatologicoListExamenfisicoestomatologicoToAttach : paciente.getExamenfisicoestomatologicoList()) {
                examenfisicoestomatologicoListExamenfisicoestomatologicoToAttach = em.getReference(examenfisicoestomatologicoListExamenfisicoestomatologicoToAttach.getClass(), examenfisicoestomatologicoListExamenfisicoestomatologicoToAttach.getIdexamenFisicoEstomatologico());
                attachedExamenfisicoestomatologicoList.add(examenfisicoestomatologicoListExamenfisicoestomatologicoToAttach);
            }
            paciente.setExamenfisicoestomatologicoList(attachedExamenfisicoestomatologicoList);
            List<Diagnostico> attachedDiagnosticoList = new ArrayList<Diagnostico>();
            for (Diagnostico diagnosticoListDiagnosticoToAttach : paciente.getDiagnosticoList()) {
                diagnosticoListDiagnosticoToAttach = em.getReference(diagnosticoListDiagnosticoToAttach.getClass(), diagnosticoListDiagnosticoToAttach.getIddiagnostico());
                attachedDiagnosticoList.add(diagnosticoListDiagnosticoToAttach);
            }
            paciente.setDiagnosticoList(attachedDiagnosticoList);
            List<Datosconsulta> attachedDatosconsultaList = new ArrayList<Datosconsulta>();
            for (Datosconsulta datosconsultaListDatosconsultaToAttach : paciente.getDatosconsultaList()) {
                datosconsultaListDatosconsultaToAttach = em.getReference(datosconsultaListDatosconsultaToAttach.getClass(), datosconsultaListDatosconsultaToAttach.getIddatosConsulta());
                attachedDatosconsultaList.add(datosconsultaListDatosconsultaToAttach);
            }
            paciente.setDatosconsultaList(attachedDatosconsultaList);
            em.persist(paciente);
            if (profesionesCodigo != null) {
                profesionesCodigo.getPacienteList().add(paciente);
                profesionesCodigo = em.merge(profesionesCodigo);
            }
            if (municipiosCodigo != null) {
                municipiosCodigo.getPacienteList().add(paciente);
                municipiosCodigo = em.merge(municipiosCodigo);
            }
            for (Medico medicoListMedico : paciente.getMedicoList()) {
                medicoListMedico.getPacienteList().add(paciente);
                medicoListMedico = em.merge(medicoListMedico);
            }
            for (Examenfisicoestomatologico examenfisicoestomatologicoListExamenfisicoestomatologico : paciente.getExamenfisicoestomatologicoList()) {
                Paciente oldPacienteidpersonaOfExamenfisicoestomatologicoListExamenfisicoestomatologico = examenfisicoestomatologicoListExamenfisicoestomatologico.getPacienteidpersona();
                examenfisicoestomatologicoListExamenfisicoestomatologico.setPacienteidpersona(paciente);
                examenfisicoestomatologicoListExamenfisicoestomatologico = em.merge(examenfisicoestomatologicoListExamenfisicoestomatologico);
                if (oldPacienteidpersonaOfExamenfisicoestomatologicoListExamenfisicoestomatologico != null) {
                    oldPacienteidpersonaOfExamenfisicoestomatologicoListExamenfisicoestomatologico.getExamenfisicoestomatologicoList().remove(examenfisicoestomatologicoListExamenfisicoestomatologico);
                    oldPacienteidpersonaOfExamenfisicoestomatologicoListExamenfisicoestomatologico = em.merge(oldPacienteidpersonaOfExamenfisicoestomatologicoListExamenfisicoestomatologico);
                }
            }
            for (Diagnostico diagnosticoListDiagnostico : paciente.getDiagnosticoList()) {
                Paciente oldPacienteIdpersonaOfDiagnosticoListDiagnostico = diagnosticoListDiagnostico.getPacienteIdpersona();
                diagnosticoListDiagnostico.setPacienteIdpersona(paciente);
                diagnosticoListDiagnostico = em.merge(diagnosticoListDiagnostico);
                if (oldPacienteIdpersonaOfDiagnosticoListDiagnostico != null) {
                    oldPacienteIdpersonaOfDiagnosticoListDiagnostico.getDiagnosticoList().remove(diagnosticoListDiagnostico);
                    oldPacienteIdpersonaOfDiagnosticoListDiagnostico = em.merge(oldPacienteIdpersonaOfDiagnosticoListDiagnostico);
                }
            }
            for (Datosconsulta datosconsultaListDatosconsulta : paciente.getDatosconsultaList()) {
                Paciente oldPacienteIdpersonaOfDatosconsultaListDatosconsulta = datosconsultaListDatosconsulta.getPacienteIdpersona();
                datosconsultaListDatosconsulta.setPacienteIdpersona(paciente);
                datosconsultaListDatosconsulta = em.merge(datosconsultaListDatosconsulta);
                if (oldPacienteIdpersonaOfDatosconsultaListDatosconsulta != null) {
                    oldPacienteIdpersonaOfDatosconsultaListDatosconsulta.getDatosconsultaList().remove(datosconsultaListDatosconsulta);
                    oldPacienteIdpersonaOfDatosconsultaListDatosconsulta = em.merge(oldPacienteIdpersonaOfDatosconsultaListDatosconsulta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPaciente(paciente.getIdpersona()) != null) {
                throw new PreexistingEntityException("Paciente " + paciente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paciente paciente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente persistentPaciente = em.find(Paciente.class, paciente.getIdpersona());
            Profesiones profesionesCodigoOld = persistentPaciente.getProfesionesCodigo();
            Profesiones profesionesCodigoNew = paciente.getProfesionesCodigo();
            Municipios municipiosCodigoOld = persistentPaciente.getMunicipiosCodigo();
            Municipios municipiosCodigoNew = paciente.getMunicipiosCodigo();
            List<Medico> medicoListOld = persistentPaciente.getMedicoList();
            List<Medico> medicoListNew = paciente.getMedicoList();
            List<Examenfisicoestomatologico> examenfisicoestomatologicoListOld = persistentPaciente.getExamenfisicoestomatologicoList();
            List<Examenfisicoestomatologico> examenfisicoestomatologicoListNew = paciente.getExamenfisicoestomatologicoList();
            List<Diagnostico> diagnosticoListOld = persistentPaciente.getDiagnosticoList();
            List<Diagnostico> diagnosticoListNew = paciente.getDiagnosticoList();
            List<Datosconsulta> datosconsultaListOld = persistentPaciente.getDatosconsultaList();
            List<Datosconsulta> datosconsultaListNew = paciente.getDatosconsultaList();
            List<String> illegalOrphanMessages = null;
            for (Examenfisicoestomatologico examenfisicoestomatologicoListOldExamenfisicoestomatologico : examenfisicoestomatologicoListOld) {
                if (!examenfisicoestomatologicoListNew.contains(examenfisicoestomatologicoListOldExamenfisicoestomatologico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Examenfisicoestomatologico " + examenfisicoestomatologicoListOldExamenfisicoestomatologico + " since its pacienteidpersona field is not nullable.");
                }
            }
            for (Diagnostico diagnosticoListOldDiagnostico : diagnosticoListOld) {
                if (!diagnosticoListNew.contains(diagnosticoListOldDiagnostico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Diagnostico " + diagnosticoListOldDiagnostico + " since its pacienteIdpersona field is not nullable.");
                }
            }
            for (Datosconsulta datosconsultaListOldDatosconsulta : datosconsultaListOld) {
                if (!datosconsultaListNew.contains(datosconsultaListOldDatosconsulta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Datosconsulta " + datosconsultaListOldDatosconsulta + " since its pacienteIdpersona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (profesionesCodigoNew != null) {
                profesionesCodigoNew = em.getReference(profesionesCodigoNew.getClass(), profesionesCodigoNew.getCodigo());
                paciente.setProfesionesCodigo(profesionesCodigoNew);
            }
            if (municipiosCodigoNew != null) {
                municipiosCodigoNew = em.getReference(municipiosCodigoNew.getClass(), municipiosCodigoNew.getCodigo());
                paciente.setMunicipiosCodigo(municipiosCodigoNew);
            }
            List<Medico> attachedMedicoListNew = new ArrayList<Medico>();
            for (Medico medicoListNewMedicoToAttach : medicoListNew) {
                medicoListNewMedicoToAttach = em.getReference(medicoListNewMedicoToAttach.getClass(), medicoListNewMedicoToAttach.getIdmedico());
                attachedMedicoListNew.add(medicoListNewMedicoToAttach);
            }
            medicoListNew = attachedMedicoListNew;
            paciente.setMedicoList(medicoListNew);
            List<Examenfisicoestomatologico> attachedExamenfisicoestomatologicoListNew = new ArrayList<Examenfisicoestomatologico>();
            for (Examenfisicoestomatologico examenfisicoestomatologicoListNewExamenfisicoestomatologicoToAttach : examenfisicoestomatologicoListNew) {
                examenfisicoestomatologicoListNewExamenfisicoestomatologicoToAttach = em.getReference(examenfisicoestomatologicoListNewExamenfisicoestomatologicoToAttach.getClass(), examenfisicoestomatologicoListNewExamenfisicoestomatologicoToAttach.getIdexamenFisicoEstomatologico());
                attachedExamenfisicoestomatologicoListNew.add(examenfisicoestomatologicoListNewExamenfisicoestomatologicoToAttach);
            }
            examenfisicoestomatologicoListNew = attachedExamenfisicoestomatologicoListNew;
            paciente.setExamenfisicoestomatologicoList(examenfisicoestomatologicoListNew);
            List<Diagnostico> attachedDiagnosticoListNew = new ArrayList<Diagnostico>();
            for (Diagnostico diagnosticoListNewDiagnosticoToAttach : diagnosticoListNew) {
                diagnosticoListNewDiagnosticoToAttach = em.getReference(diagnosticoListNewDiagnosticoToAttach.getClass(), diagnosticoListNewDiagnosticoToAttach.getIddiagnostico());
                attachedDiagnosticoListNew.add(diagnosticoListNewDiagnosticoToAttach);
            }
            diagnosticoListNew = attachedDiagnosticoListNew;
            paciente.setDiagnosticoList(diagnosticoListNew);
            List<Datosconsulta> attachedDatosconsultaListNew = new ArrayList<Datosconsulta>();
            for (Datosconsulta datosconsultaListNewDatosconsultaToAttach : datosconsultaListNew) {
                datosconsultaListNewDatosconsultaToAttach = em.getReference(datosconsultaListNewDatosconsultaToAttach.getClass(), datosconsultaListNewDatosconsultaToAttach.getIddatosConsulta());
                attachedDatosconsultaListNew.add(datosconsultaListNewDatosconsultaToAttach);
            }
            datosconsultaListNew = attachedDatosconsultaListNew;
            paciente.setDatosconsultaList(datosconsultaListNew);
            paciente = em.merge(paciente);
            if (profesionesCodigoOld != null && !profesionesCodigoOld.equals(profesionesCodigoNew)) {
                profesionesCodigoOld.getPacienteList().remove(paciente);
                profesionesCodigoOld = em.merge(profesionesCodigoOld);
            }
            if (profesionesCodigoNew != null && !profesionesCodigoNew.equals(profesionesCodigoOld)) {
                profesionesCodigoNew.getPacienteList().add(paciente);
                profesionesCodigoNew = em.merge(profesionesCodigoNew);
            }
            if (municipiosCodigoOld != null && !municipiosCodigoOld.equals(municipiosCodigoNew)) {
                municipiosCodigoOld.getPacienteList().remove(paciente);
                municipiosCodigoOld = em.merge(municipiosCodigoOld);
            }
            if (municipiosCodigoNew != null && !municipiosCodigoNew.equals(municipiosCodigoOld)) {
                municipiosCodigoNew.getPacienteList().add(paciente);
                municipiosCodigoNew = em.merge(municipiosCodigoNew);
            }
            for (Medico medicoListOldMedico : medicoListOld) {
                if (!medicoListNew.contains(medicoListOldMedico)) {
                    medicoListOldMedico.getPacienteList().remove(paciente);
                    medicoListOldMedico = em.merge(medicoListOldMedico);
                }
            }
            for (Medico medicoListNewMedico : medicoListNew) {
                if (!medicoListOld.contains(medicoListNewMedico)) {
                    medicoListNewMedico.getPacienteList().add(paciente);
                    medicoListNewMedico = em.merge(medicoListNewMedico);
                }
            }
            for (Examenfisicoestomatologico examenfisicoestomatologicoListNewExamenfisicoestomatologico : examenfisicoestomatologicoListNew) {
                if (!examenfisicoestomatologicoListOld.contains(examenfisicoestomatologicoListNewExamenfisicoestomatologico)) {
                    Paciente oldPacienteidpersonaOfExamenfisicoestomatologicoListNewExamenfisicoestomatologico = examenfisicoestomatologicoListNewExamenfisicoestomatologico.getPacienteidpersona();
                    examenfisicoestomatologicoListNewExamenfisicoestomatologico.setPacienteidpersona(paciente);
                    examenfisicoestomatologicoListNewExamenfisicoestomatologico = em.merge(examenfisicoestomatologicoListNewExamenfisicoestomatologico);
                    if (oldPacienteidpersonaOfExamenfisicoestomatologicoListNewExamenfisicoestomatologico != null && !oldPacienteidpersonaOfExamenfisicoestomatologicoListNewExamenfisicoestomatologico.equals(paciente)) {
                        oldPacienteidpersonaOfExamenfisicoestomatologicoListNewExamenfisicoestomatologico.getExamenfisicoestomatologicoList().remove(examenfisicoestomatologicoListNewExamenfisicoestomatologico);
                        oldPacienteidpersonaOfExamenfisicoestomatologicoListNewExamenfisicoestomatologico = em.merge(oldPacienteidpersonaOfExamenfisicoestomatologicoListNewExamenfisicoestomatologico);
                    }
                }
            }
            for (Diagnostico diagnosticoListNewDiagnostico : diagnosticoListNew) {
                if (!diagnosticoListOld.contains(diagnosticoListNewDiagnostico)) {
                    Paciente oldPacienteIdpersonaOfDiagnosticoListNewDiagnostico = diagnosticoListNewDiagnostico.getPacienteIdpersona();
                    diagnosticoListNewDiagnostico.setPacienteIdpersona(paciente);
                    diagnosticoListNewDiagnostico = em.merge(diagnosticoListNewDiagnostico);
                    if (oldPacienteIdpersonaOfDiagnosticoListNewDiagnostico != null && !oldPacienteIdpersonaOfDiagnosticoListNewDiagnostico.equals(paciente)) {
                        oldPacienteIdpersonaOfDiagnosticoListNewDiagnostico.getDiagnosticoList().remove(diagnosticoListNewDiagnostico);
                        oldPacienteIdpersonaOfDiagnosticoListNewDiagnostico = em.merge(oldPacienteIdpersonaOfDiagnosticoListNewDiagnostico);
                    }
                }
            }
            for (Datosconsulta datosconsultaListNewDatosconsulta : datosconsultaListNew) {
                if (!datosconsultaListOld.contains(datosconsultaListNewDatosconsulta)) {
                    Paciente oldPacienteIdpersonaOfDatosconsultaListNewDatosconsulta = datosconsultaListNewDatosconsulta.getPacienteIdpersona();
                    datosconsultaListNewDatosconsulta.setPacienteIdpersona(paciente);
                    datosconsultaListNewDatosconsulta = em.merge(datosconsultaListNewDatosconsulta);
                    if (oldPacienteIdpersonaOfDatosconsultaListNewDatosconsulta != null && !oldPacienteIdpersonaOfDatosconsultaListNewDatosconsulta.equals(paciente)) {
                        oldPacienteIdpersonaOfDatosconsultaListNewDatosconsulta.getDatosconsultaList().remove(datosconsultaListNewDatosconsulta);
                        oldPacienteIdpersonaOfDatosconsultaListNewDatosconsulta = em.merge(oldPacienteIdpersonaOfDatosconsultaListNewDatosconsulta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = paciente.getIdpersona();
                if (findPaciente(id) == null) {
                    throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paciente paciente;
            try {
                paciente = em.getReference(Paciente.class, id);
                paciente.getIdpersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paciente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Examenfisicoestomatologico> examenfisicoestomatologicoListOrphanCheck = paciente.getExamenfisicoestomatologicoList();
            for (Examenfisicoestomatologico examenfisicoestomatologicoListOrphanCheckExamenfisicoestomatologico : examenfisicoestomatologicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paciente (" + paciente + ") cannot be destroyed since the Examenfisicoestomatologico " + examenfisicoestomatologicoListOrphanCheckExamenfisicoestomatologico + " in its examenfisicoestomatologicoList field has a non-nullable pacienteidpersona field.");
            }
            List<Diagnostico> diagnosticoListOrphanCheck = paciente.getDiagnosticoList();
            for (Diagnostico diagnosticoListOrphanCheckDiagnostico : diagnosticoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paciente (" + paciente + ") cannot be destroyed since the Diagnostico " + diagnosticoListOrphanCheckDiagnostico + " in its diagnosticoList field has a non-nullable pacienteIdpersona field.");
            }
            List<Datosconsulta> datosconsultaListOrphanCheck = paciente.getDatosconsultaList();
            for (Datosconsulta datosconsultaListOrphanCheckDatosconsulta : datosconsultaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Paciente (" + paciente + ") cannot be destroyed since the Datosconsulta " + datosconsultaListOrphanCheckDatosconsulta + " in its datosconsultaList field has a non-nullable pacienteIdpersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Profesiones profesionesCodigo = paciente.getProfesionesCodigo();
            if (profesionesCodigo != null) {
                profesionesCodigo.getPacienteList().remove(paciente);
                profesionesCodigo = em.merge(profesionesCodigo);
            }
            Municipios municipiosCodigo = paciente.getMunicipiosCodigo();
            if (municipiosCodigo != null) {
                municipiosCodigo.getPacienteList().remove(paciente);
                municipiosCodigo = em.merge(municipiosCodigo);
            }
            List<Medico> medicoList = paciente.getMedicoList();
            for (Medico medicoListMedico : medicoList) {
                medicoListMedico.getPacienteList().remove(paciente);
                medicoListMedico = em.merge(medicoListMedico);
            }
            em.remove(paciente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paciente> findPacienteEntities() {
        return findPacienteEntities(true, -1, -1);
    }

    public List<Paciente> findPacienteEntities(int maxResults, int firstResult) {
        return findPacienteEntities(false, maxResults, firstResult);
    }

    private List<Paciente> findPacienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paciente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Paciente findPaciente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paciente.class, id);
        } finally {
            em.close();
        }
    }

    public int getPacienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paciente> rt = cq.from(Paciente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}