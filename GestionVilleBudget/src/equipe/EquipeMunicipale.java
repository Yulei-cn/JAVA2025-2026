package equipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représente l'équipe municipale complète de Dauphine City.
 * 
 * <p>Une équipe municipale est composée de :
 * <ul>
 *   <li>Un élu unique</li>
 *   <li>Trois évaluateurs (un par type de coût)</li>
 *   <li>Plusieurs experts</li>
 * </ul>
 * </p>
 * 
 * <p>L'équipe est capable d'exécuter un cycle de simulation complet :
 * <ol>
 *   <li>Les experts proposent des projets</li>
 *   <li>Les évaluateurs évaluent les coûts</li>
 *   <li>L'élu évalue le bénéfice</li>
 *   <li>Les projets complets sont ajoutés à la liste des projets étudiés</li>
 * </ol>
 * </p>
 * 
 * @author Votre Nom
 * @version 1.0
 * @see Elu
 * @see Evaluateur
 * @see Expert
 * @see Projet
 */
public class EquipeMunicipale {
    
    /** L'élu de la municipalité */
    private Elu elu;
    
    /** Les trois évaluateurs, indexés par leur type de coût */
    private Map<TypeCout, Evaluateur> evaluateurs;
    
    /** La liste des experts de l'équipe */
    private List<Expert> experts;
    
    /** La liste des projets complètement évalués */
    private List<Projet> projetsEtudies;
    
    /**
     * Construit une nouvelle équipe municipale.
     * 
     * <p>Cette méthode vérifie que :
     * <ul>
     *   <li>Il y a exactement 3 évaluateurs</li>
     *   <li>Chaque évaluateur a une spécialisation différente</li>
     *   <li>Les trois types de coûts sont couverts</li>
     * </ul>
     * </p>
     * 
     * @param elu l'élu de la municipalité (ne doit pas être null)
     * @param listeEvaluateurs la liste des évaluateurs (doit contenir exactement 3 éléments)
     * @param experts la liste des experts (peut être vide mais pas null)
     * @throws IllegalArgumentException si le nombre d'évaluateurs n'est pas 3,
     *                                  ou si des types de coûts sont manquants ou dupliqués
     */
    public EquipeMunicipale(Elu elu, List<Evaluateur> listeEvaluateurs, List<Expert> experts) {
        if (listeEvaluateurs.size() != 3) {
            throw new IllegalArgumentException("必须有且仅有3个评估者！");
        }
        
        this.elu = elu;
        this.experts = new ArrayList<>(experts);
        this.projetsEtudies = new ArrayList<>();
        
        this.evaluateurs = new HashMap<>();
        for (Evaluateur eval : listeEvaluateurs) {
            TypeCout type = eval.getSpecialisation();
            if (this.evaluateurs.containsKey(type)) {
                throw new IllegalArgumentException("评估者类型重复：" + type);
            }
            this.evaluateurs.put(type, eval);
        }
        
        if (!evaluateurs.containsKey(TypeCout.ECONOMIQUE) ||
            !evaluateurs.containsKey(TypeCout.SOCIAL) ||
            !evaluateurs.containsKey(TypeCout.ENVIRONNEMENTAL)) {
            throw new IllegalArgumentException("必须包含三种类型的评估者：经济、社会、环境");
        }
    }
    
    /**
     * Retourne l'élu de l'équipe municipale.
     * 
     * @return l'élu
     */
    public Elu getElu() {
        return elu;
    }
    
    /**
     * Retourne la map des évaluateurs indexés par type de coût.
     * 
     * @return une map associant chaque type de coût à son évaluateur
     */
    public Map<TypeCout, Evaluateur> getEvaluateurs() {
        return evaluateurs;
    }
    
    /**
     * Retourne la liste des experts de l'équipe.
     * 
     * @return la liste des experts
     */
    public List<Expert> getExperts() {
        return experts;
    }
    
    /**
     * Retourne la liste de tous les projets évalués par l'équipe.
     * 
     * @return la liste des projets complets
     */
    public List<Projet> getProjetsEtudies() {
        return projetsEtudies;
    }
    
    /**
     * Exécute un cycle de simulation complet.
     * 
     * <p>Processus :
     * <ol>
     *   <li>Chaque expert propose un nombre donné de projets</li>
     *   <li>Pour chaque projet proposé :
     *     <ul>
     *       <li>Chaque évaluateur attribue le coût correspondant à sa spécialisation</li>
     *       <li>L'élu attribue le bénéfice</li>
     *       <li>Si le projet est complet, il est ajouté à la liste des projets étudiés</li>
     *     </ul>
     *   </li>
     * </ol>
     * </p>
     * 
     * <p>Cette méthode affiche des informations sur la console pendant l'exécution.</p>
     * 
     * @param nombreProjetsParExpert le nombre de projets que chaque expert doit proposer
     * @see Expert#proposerProjets(int)
     * @see Evaluateur#evaluerCout(Projet)
     * @see Elu#evaluerBenefice(Projet)
     */
    public void executerCycleSimulation(int nombreProjetsParExpert) {
        System.out.println("\n========== 开始模拟循环 ==========");
        System.out.println("每个专家将提出 " + nombreProjetsParExpert + " 个项目\n");
        
        List<Projet> nouveauxProjets = new ArrayList<>();
        for (Expert expert : experts) {
            System.out.println("专家 " + expert.getPrenom() + " " + expert.getNom() + " 正在提出项目...");
            List<Projet> projetsExpert = expert.proposerProjets(nombreProjetsParExpert);
            nouveauxProjets.addAll(projetsExpert);
        }
        
        System.out.println("\n总共提出了 " + nouveauxProjets.size() + " 个项目");
        System.out.println("\n开始评估每个项目...\n");
        
        int compteurProjetsValides = 0;
        for (Projet projet : nouveauxProjets) {
            System.out.println("正在评估：" + projet.getTitre());
            
            for (Map.Entry<TypeCout, Evaluateur> entry : evaluateurs.entrySet()) {
                Evaluateur evaluateur = entry.getValue();
                evaluateur.evaluerCout(projet);
            }
            
            elu.evaluerBenefice(projet);
            
            if (projet.estComplet()) {
                projetsEtudies.add(projet);
                compteurProjetsValides++;
                System.out.println("  ✓ 项目评估完成");
            } else {
                System.out.println("  ✗ 项目不完整");
            }
        }
        
        System.out.println("\n========== 模拟循环结束 ==========");
        System.out.println("有效项目数：" + compteurProjetsValides);
        System.out.println("累计研究项目数：" + projetsEtudies.size());
    }
    
    /**
     * Affiche les informations sur tous les membres de l'équipe municipale.
     * 
     * <p>Cette méthode affiche l'élu, les évaluateurs et les experts
     * de manière formatée sur la console.</p>
     */
    public void afficherEquipe() {
        System.out.println("\n===== Dauphine City 市政团队 =====");
        System.out.println("\n【当选官员】");
        System.out.println("  " + elu);
        
        System.out.println("\n【评估者】");
        for (Evaluateur eval : evaluateurs.values()) {
            System.out.println("  " + eval);
        }
        
        System.out.println("\n【专家】");
        for (Expert expert : experts) {
            System.out.println("  " + expert);
        }
        System.out.println("=====================================\n");
    }
    
    /**
     * Affiche tous les projets étudiés par l'équipe.
     * 
     * <p>Cette méthode affiche la liste complète des projets évalués
     * avec toutes leurs caractéristiques.</p>
     */
    public void afficherProjets() {
        System.out.println("\n===== 已研究的项目列表 =====");
        if (projetsEtudies.isEmpty()) {
            System.out.println("（暂无项目）");
        } else {
            for (int i = 0; i < projetsEtudies.size(); i++) {
                System.out.println((i + 1) + ". " + projetsEtudies.get(i));
            }
        }
        System.out.println("============================\n");
    }
    
    /**
     * Réinitialise la liste des projets étudiés.
     * 
     * <p>Cette méthode supprime tous les projets de la liste,
     * permettant de recommencer une nouvelle série de simulations.</p>
     */
    public void reinitialiserProjets() {
        projetsEtudies.clear();
        System.out.println("项目列表已清空");
    }
}