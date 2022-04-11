namespace competitionGUi
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.userNameTB = new System.Windows.Forms.TextBox();
            this.passwordTB = new System.Windows.Forms.TextBox();
            this.competitionLB = new System.Windows.Forms.Label();
            this.loginBT = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // userNameTB
            // 
            this.userNameTB.Location = new System.Drawing.Point(118, 127);
            this.userNameTB.Name = "userNameTB";
            this.userNameTB.Size = new System.Drawing.Size(223, 31);
            this.userNameTB.TabIndex = 0;
            // 
            // passwordTB
            // 
            this.passwordTB.Location = new System.Drawing.Point(118, 177);
            this.passwordTB.Name = "passwordTB";
            this.passwordTB.PasswordChar = '*';
            this.passwordTB.Size = new System.Drawing.Size(223, 31);
            this.passwordTB.TabIndex = 1;
            // 
            // competitionLB
            // 
            this.competitionLB.AutoSize = true;
            this.competitionLB.Location = new System.Drawing.Point(168, 71);
            this.competitionLB.Name = "competitionLB";
            this.competitionLB.Size = new System.Drawing.Size(111, 25);
            this.competitionLB.TabIndex = 2;
            this.competitionLB.Text = "Competition";
            // 
            // loginBT
            // 
            this.loginBT.Location = new System.Drawing.Point(167, 255);
            this.loginBT.Name = "loginBT";
            this.loginBT.Size = new System.Drawing.Size(112, 34);
            this.loginBT.TabIndex = 3;
            this.loginBT.Text = "LOGIN";
            this.loginBT.UseVisualStyleBackColor = true;
            this.loginBT.Click += new System.EventHandler(this.loginBT_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(10F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(485, 450);
            this.Controls.Add(this.loginBT);
            this.Controls.Add(this.competitionLB);
            this.Controls.Add(this.passwordTB);
            this.Controls.Add(this.userNameTB);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private TextBox userNameTB;
        private TextBox passwordTB;
        private Label competitionLB;
        private Button loginBT;
    }
}