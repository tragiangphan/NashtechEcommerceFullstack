import { FormEvent, useRef } from "react";
import { signIn } from "../../services/user/AuthService";
import { useNavigate } from "react-router-dom";


export const SignInComponent = () => {
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);

  const navigator = useNavigate();

  const handleSignIn = (event: FormEvent) => {
    event.preventDefault();
    if (emailRef.current && passwordRef.current) {
      const signInData: SignInRequest = {
        email: emailRef.current.value,
        password: passwordRef.current.value,
      };

      console.log(signInData.email, ' ', signInData.password);
      signIn(signInData)
        .then((res) => {
          console.log(res.data);
          navigator("/home");
        })
        .catch((err) => {
          console.error(err);
        });
    } else {
      console.error("Email or password ref is not assigned");
    }
  };

  return (
    <div className="container">
      <h1>Sign In</h1>
      <form className="" onSubmit={handleSignIn}>
        <div className="mb-3">
          <label htmlFor="exampleInputEmail1" className="form-label">Email address</label>
          <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" ref={emailRef} />
          <div id="emailHelp" className="form-text">We'll never share your email with anyone else.</div>
        </div>
        <div className="mb-3">
          <label htmlFor="exampleInputPassword1" className="form-label">Password</label>
          <input type="password" className="form-control" id="exampleInputPassword1" ref={passwordRef}/>
        </div>
        <div className="mb-3 form-check">
          <input type="checkbox" className="form-check-input" id="exampleCheck1" />
          <label className="form-check-label" htmlFor="exampleCheck1">Check me out</label>
        </div>
        <button type="submit" className="btn btn-primary">Submit</button>
      </form>
    </div>
  )
}
