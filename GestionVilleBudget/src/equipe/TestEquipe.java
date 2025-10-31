package equipe;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * 测试类：验证市政团队的功能
 */
public class TestEquipe {
    
    public static void main(String[] args) {
        System.out.println("🏛️  Dauphine City 预算管理系统测试\n");
        
        // 1. 创建当选官员
        Elu elu = new Elu("Martin", "Pierre", 45);
        
        // 2. 创建3个评估者（每种类型一个）
        List<Evaluateur> evaluateurs = new ArrayList<>();
        evaluateurs.add(new Evaluateur("Dupont", "Marie", 35, TypeCout.ECONOMIQUE));
        evaluateurs.add(new Evaluateur("Durant", "Sophie", 40, TypeCout.SOCIAL));
        evaluateurs.add(new Evaluateur("Bernard", "Luc", 38, TypeCout.ENVIRONNEMENTAL));
        
        // 3. 创建专家
        List<Expert> experts = new ArrayList<>();
        experts.add(new Expert("Leroy", "Jean", 42, 
                    EnumSet.of(Secteur.SPORT, Secteur.EDUCATION)));
        experts.add(new Expert("Moreau", "Claire", 39, 
                    EnumSet.of(Secteur.SANTE, Secteur.CULTURE)));
        experts.add(new Expert("Simon", "Paul", 44, 
                    EnumSet.of(Secteur.ATTRACTIVITE_ECONOMIQUE)));
        
        // 4. 创建市政团队
        EquipeMunicipale equipe = new EquipeMunicipale(elu, evaluateurs, experts);
        
        // 5. 显示团队信息
        equipe.afficherEquipe();
        
        // 6. 执行模拟循环
        equipe.executerCycleSimulation(2);  // 每个专家提出2个项目
        
        // 7. 显示生成的项目
        equipe.afficherProjets();
        
        // 8. 再执行一次模拟
        System.out.println("\n\n🔄 执行第二次模拟循环...\n");
        equipe.executerCycleSimulation(1);  // 每个专家再提出1个项目
        equipe.afficherProjets();
        
        System.out.println("\n✅ 测试完成！");
    }
}
