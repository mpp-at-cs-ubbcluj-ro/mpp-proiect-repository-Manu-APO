using CompetitionCSh.domain.entities;
using CompetitionCSh.service;

namespace competitionGUi
{
    public partial class Form1 : Form
    {
        private CompetitionService _competitionService;

        public Form1(CompetitionService competitionService)
        {
            InitializeComponent();
            _competitionService = competitionService;
        }

        private void loginBT_Click(object sender, EventArgs e)
        {
            string username = userNameTB.Text;
            string password = passwordTB.Text;

            Registry registry = _competitionService.findRegistryByCredetials(username, password);

            if (registry == null)
            {
                MessageBox.Show("Error message", "Invalid username or password!");
                return;
            }
            Dashboard dashboard = new Dashboard(registry,_competitionService, this);
            this.Hide();
            dashboard.Show();
        }
    }
}