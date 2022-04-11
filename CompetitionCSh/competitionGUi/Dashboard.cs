using CompetitionCSh.domain.entities;
using CompetitionCSh.service;

namespace competitionGUi
{
    public partial class Dashboard : Form
    {
        private Registry _registry;
        private CompetitionService _competitionService;
        private Form1 _loginForm1;

        public Dashboard(Registry registry, CompetitionService competitionService, Form1 loginForm1)
        {
            InitializeComponent();
            _registry = registry;
            _competitionService = competitionService;
            _loginForm1 = loginForm1;
        }
    }
}
