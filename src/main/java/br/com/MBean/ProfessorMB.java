package br.com.MBean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.DAO.professorDAO;
import br.com.entities.CD;
import br.com.entities.Disciplina;
import br.com.entities.Professor;

@ManagedBean
@ViewScoped
public class ProfessorMB {
	int idC;
	professorDAO pDAO = new professorDAO();
	Professor prof = new Professor();
	Professor selc;
	CD cd = new CD();
	CD cdSelc;
	Professor tcoo;
	List<Professor> profL;
	List<Professor> lista;
	List<Disciplina> d = pDAO.listarDisciplina();
	List<CD> cdl;
	FacesContext context;

	public ProfessorMB() {
		listarP();
	}

	public void salvar() {
		context = FacesContext.getCurrentInstance();
		if (prof.getId() != null) {
			Professor p = pDAO.buscaProfessor(prof.getId());
			if (p != null && p.getId().equals(prof.getId())) {
				editarProfessor();
			}
		} else {
			criarProfessor();
		}
	}

	public void editarProfessor() {
		if (testarCampos()) {
			if (pDAO.editar(prof)) {
				System.out.println("SDSA:Colaborador alterado.");
				context.addMessage(null, new FacesMessage("Sucesso", "Colaborador alterado."));
				listarP();
				zerar();
			} else {
				System.out.println("SDSA:Erro na alter��o do colaborador.");
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na alter��o do colaborador."));
				listarP();
			}
		} else {
			System.out.println("SDSA:Campo vazio.");
		}
	}

	public void criarProfessor() {
		if (testarCampos()) {
			if (pDAO.inserir(prof)) {
				System.out.println("SDSA:Colaborador criado.");
				context.addMessage(null, new FacesMessage("Sucesso", "Colaborador criado."));
				listarP();
				zerar();
			} else {
				System.out.println("SDSA:Erro na cria��o do colaborador.");
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao criar o colaborador."));
				listarP();
			}
		} else {
			System.out.println("SDSA:Campo vazio.");
		}
	}

	private boolean testarCampos() {
		if ((prof.getNome().equals("")) || (prof.getCarga_hora() == null) || (prof.getCarga_hora() == 0) || (prof.getTipo() == null)) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Campo(s) vazio(s)", "Algum campo est� vazio."));
			return false;
		} else {
			return true;
		}
	}

	public void colocarDisciplina() {
		context = FacesContext.getCurrentInstance();
		cd.setId_colaborador(selc.getId());
		if (pDAO.inserirCD(cd)) {
			System.out.println("SDSA:Disciplina alocada.");
			context.addMessage(null,
					new FacesMessage("Aloca��o concluida", "Disciplina foi alocada com o colaborador(a)."));
			cDisciplina();
			cdZerar();
		} else {
			System.out.println("SDSA:Erro na aloca��o da disciplina.");
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Campo vazio", "A disciplina n�o foi escolhida."));
			cDisciplina();
		}
	}

	public void zerar() {
		pDAO = new professorDAO();
		prof = new Professor();
		cd = new CD();
		lista = null;
		selc = null;
	}

	public void cdZerar() {
		cdSelc = null;
		cd = new CD();
		pDAO = new professorDAO();
		prof = new Professor();
	}

	public void editar() {
		prof = selc;
	}

	public void excluir() {
		context = FacesContext.getCurrentInstance();
		if (pDAO.excluir(selc.getId())) {
			System.out.println("SDSA:Colaborador " + selc.getNome() + " excluido.");
			context.addMessage(null,
					new FacesMessage("Excluido", "Colaborador(a) " + selc.getNome() + " removido(a)."));
			delit();
			listarP();
			zerar();
		} else {
			System.out.println("SDSA:Erro para excluir colaborador.");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Para excluir o professor � necess�rio desalocalo da(s) disciplina(s)."));
		}
	}

	public void cdExcluir() {
		context = FacesContext.getCurrentInstance();
		if (pDAO.cdExcluir(cdSelc.getId())) {
			System.out.println("SDSA:Colaborador(a) " + selc.getNome() + " removido(a) da disciplina.");
			context.addMessage(null,
					new FacesMessage("Desalocado", "Colaborador(a) " + selc.getNome() + " removido(a) da disciplina."));
			cDisciplina();
			cdZerar();
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao tentar excluir."));
		}
	}
	
	public void delit() {
		if(pDAO.delit(selc.getId())) {
			System.out.println("SDSA:Aula deletada.");
		}else {
			System.out.println("SDSA:Erro ao deletar aula.");
		}
	}

	public String fechar() {
		zerar();
		listarP();
		return "telaCoordenador";
	}

	public void cDisciplina() {
		listarDisciplina();
		listarP();
		cdl = pDAO.listarCd(selc.getId());
	}

	public void listarDisciplina() {
		d = pDAO.listarDisciplina();
	}

	public void listarP() {
		profL = pDAO.listarTodos();
	}

	public List<Disciplina> getD() {
		return d;
	}

	public List<CD> getCdl() {
		return cdl;
	}

	public void setCdl(List<CD> cdl) {
		this.cdl = cdl;
	}

	public CD getCd() {
		return cd;
	}

	public void setCd(CD cd) {
		this.cd = cd;
	}

	public void setD(List<Disciplina> d) {
		this.d = d;
	}

	public int getIdC() {
		return idC;
	}

	public void setIdC(int idC) {
		this.idC = idC;
	}

	public Professor getProf() {
		return prof;
	}

	public void setProf(Professor prof) {
		this.prof = prof;
	}

	public List<Professor> getProfL() {
		return profL;
	}

	public void setProfL(List<Professor> profL) {
		this.profL = profL;
	}

	public professorDAO getpDAO() {
		return pDAO;
	}

	public void setpDAO(professorDAO pDAO) {
		this.pDAO = pDAO;
	}

	public Professor getSelc() {
		return selc;
	}

	public void setSelc(Professor selc) {
		this.selc = selc;
	}

	public CD getCdSelc() {
		return cdSelc;
	}

	public void setCdSelc(CD cdSelc) {
		this.cdSelc = cdSelc;
	}

	public Professor getTcoo() {
		return tcoo;
	}

	public void setTcoo(Professor tcoo) {
		this.tcoo = tcoo;
	}

	public List<Professor> getLista() {
		return lista;
	}

	public void setLista(List<Professor> lista) {
		this.lista = lista;
	}

}